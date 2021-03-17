package ai.leantech.delivery.repository;

import ai.leantech.delivery.model.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface UserRepository extends JpaRepositoryImplementation<User, Long> {

    User findByLogin(String login);

}
