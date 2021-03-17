package ai.leantech.delivery.repository;

import ai.leantech.delivery.model.Role;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;

public interface RoleRepository extends JpaRepositoryImplementation<Role, Long> {

    Optional<Role> findByName(String name);

}
