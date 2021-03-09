package ai.leantech.delivery.dao;

import ai.leantech.delivery.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleDAO {
    Role findByName(String role);

    Optional<Role> findOne(long id);
    List<Role> findAll();
    void create(Role role);
    Role update(Role role);
    void delete(Role role);
    void deleteById(long roleId);
}
