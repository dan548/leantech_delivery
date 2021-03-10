package ai.leantech.delivery.dao.impl;

import ai.leantech.delivery.dao.AbstractJpaDAO;
import ai.leantech.delivery.dao.IUserDAO;
import ai.leantech.delivery.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDAO extends AbstractJpaDAO<User> implements IUserDAO {

    public UserDAO() {
        super();

        setClazz(User.class);
    }

    @Override
    public User findByLogin(String login) {
        return entityManager.createQuery(
                "SELECT u FROM User u WHERE u.login LIKE :username", User.class)
                .setParameter("username", login)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
