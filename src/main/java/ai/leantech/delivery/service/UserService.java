package ai.leantech.delivery.service;

import ai.leantech.delivery.model.RoleType;
import ai.leantech.delivery.model.User;
import ai.leantech.delivery.repository.RoleRepository;
import ai.leantech.delivery.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(final UserRepository userRepository, final RoleRepository roleRepository, final PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RoleType getRoleTypeByName(String role) {
        return Arrays.stream(RoleType.values())
                .filter(x -> x.toString().equals(role))
                .findFirst()
                .orElse(null);
    }

    public void addNewUser(User user, RoleType role) {
        /* Role userRole = roleDAO.findByName("ROLE_" + role);
        user.setRoles(Set.of(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.create(user);
        Set<User> roleUsers = userRole.getUsers();
        roleUsers.add(user);
        roleDAO.update(userRole);
        */
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

}
