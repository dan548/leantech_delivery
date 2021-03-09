package ai.leantech.delivery.dao;

import ai.leantech.delivery.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {

    Optional<User> findOne(long id);
    List<User> findAll();
    void create(User user);
    User update(User user);
    void delete(User user);
    void deleteById(long userId);

    User findByLogin(String login);
}
