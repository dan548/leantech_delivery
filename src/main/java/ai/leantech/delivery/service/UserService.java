package ai.leantech.delivery.service;

import ai.leantech.delivery.dao.IRoleDAO;
import ai.leantech.delivery.dao.IUserDAO;
import ai.leantech.delivery.model.Role;
import ai.leantech.delivery.model.RoleType;
import ai.leantech.delivery.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final IUserDAO userDAO;
    private final IRoleDAO roleDAO;

    public UserService(final IUserDAO userDAO, final IRoleDAO roleDAO, final PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public void addNewUser(User user, RoleType role) {
        Role userRole = roleDAO.findByName("ROLE_" + role);
        user.setRoles(Set.of(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.create(user);
        Set<User> roleUsers = userRole.getUsers();
        roleUsers.add(user);
        roleDAO.update(userRole);
    }

    public User findByLogin(String login) {
        return userDAO.findByLogin(login);
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
