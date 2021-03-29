package ai.leantech.delivery.service;

import ai.leantech.delivery.config.jwt.JwtProvider;
import ai.leantech.delivery.controller.model.AuthResponse;
import ai.leantech.delivery.controller.model.RegistrationRequest;
import ai.leantech.delivery.controller.model.user.AdminRegistrationRequest;
import ai.leantech.delivery.controller.model.user.UserDtoConverter;
import ai.leantech.delivery.controller.model.user.UserResponse;
import ai.leantech.delivery.entity.Role;
import ai.leantech.delivery.model.RoleType;
import ai.leantech.delivery.entity.User;
import ai.leantech.delivery.repository.RoleRepository;
import ai.leantech.delivery.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final UserDtoConverter userDtoConverter;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SecurityContext securityContext = SecurityContextHolder.getContext();

    public UserService(final UserRepository userRepository,
                       final RoleRepository roleRepository,
                       final PasswordEncoder passwordEncoder,
                       final JwtProvider jwtProvider,
                       final ObjectMapper objectMapper,
                       final UserDtoConverter userDtoConverter) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.objectMapper = objectMapper;
        this.userDtoConverter = userDtoConverter;
    }

    public UserResponse addNewUser(RegistrationRequest registrationRequest, RoleType role) {
        User user = new User();
        user.setLogin(registrationRequest.getLogin());
        Role userRole = roleRepository.findByName("ROLE_" + role)
                .orElseThrow(RuntimeException::new);
        user.setRoles(Set.of(userRole));
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        User result = userRepository.save(user);
        Set<User> roleUsers = userRole.getUsers();
        roleUsers.add(user);
        roleRepository.save(userRole);
        return userDtoConverter.convertUserToUserResp(result);
    }

    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional(readOnly = true)
    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public AuthResponse authByLoginAndPassword(String login, String password) {
        User user = findByLoginAndPassword(login, password);
        String token = jwtProvider.generateToken(user.getLogin(), user.getRoles());
        return new AuthResponse(token);
    }

    @Transactional(readOnly = true)
    public User getUserByAuthentication() {
        Authentication auth = securityContext.getAuthentication();
        Object principal = auth.getPrincipal();
        String login = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        return userRepository.findByLogin(login);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserResponseByAuthentication(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String login = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        return userDtoConverter.convertUserToUserResp(userRepository.findByLogin(login));
    }

    public UserResponse addNewUser(AdminRegistrationRequest request) {
        User user = new User();
        user.setLogin(request.getLogin());
        Set<Role> roles = request.getRoles().stream()
                .map(role -> roleRepository.findByName("ROLE_" + role)
                .orElseThrow(RuntimeException::new))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User result = userRepository.save(user);
        roles.forEach(role -> {
            role.getUsers().add(user);
            roleRepository.save(role);
        });
        return userDtoConverter.convertUserToUserResp(result);
    }

    public UserResponse editUser(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id %s not found", id))
        );
        User newUser = applyPatchToUser(patch, user);
        if (!user.getPassword().equals(newUser.getPassword())) {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        return userDtoConverter.convertUserToUserResp(userRepository.save(newUser));
    }

    private User applyPatchToUser(JsonPatch patch, User targetUser) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetUser, JsonNode.class));
        return objectMapper.treeToValue(patched, User.class);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userDtoConverter::convertUserToUserResp)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id).map(userDtoConverter::convertUserToUserResp).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id %s not found", id))
        );
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
