package ai.leantech.delivery.service;

import ai.leantech.delivery.config.jwt.JwtProvider;
import ai.leantech.delivery.controller.model.AuthResponse;
import ai.leantech.delivery.controller.model.RegistrationRequest;
import ai.leantech.delivery.model.Role;
import ai.leantech.delivery.model.RoleType;
import ai.leantech.delivery.model.User;
import ai.leantech.delivery.repository.RoleRepository;
import ai.leantech.delivery.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(final UserRepository userRepository,
                       final RoleRepository roleRepository,
                       final PasswordEncoder passwordEncoder,
                       final JwtProvider jwtProvider) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public void addNewUser(RegistrationRequest registrationRequest, RoleType role) {
        User user = new User();
        user.setLogin(registrationRequest.getLogin());
        Role userRole = roleRepository.findByName("ROLE_" + role)
                .orElseThrow(RuntimeException::new);
        user.setRoles(Set.of(userRole));
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userRepository.save(user);
        Set<User> roleUsers = userRole.getUsers();
        roleUsers.add(user);
        roleRepository.save(userRole);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public AuthResponse authByLoginAndPassword(String login, String password) {
        User user = findByLoginAndPassword(login, password);
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }

    public User getUserByAuthentication(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String login = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        return userRepository.findByLogin(login);
    }

}
