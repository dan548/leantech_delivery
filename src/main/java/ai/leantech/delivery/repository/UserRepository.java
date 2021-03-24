package ai.leantech.delivery.repository;

import ai.leantech.delivery.entity.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepositoryImplementation<User, Long> {

    @Transactional(readOnly = true)
    User findByLogin(String login);

}
