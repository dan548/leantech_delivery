package ai.leantech.delivery.dao.impl;

import ai.leantech.delivery.dao.AbstractJpaDAO;
import ai.leantech.delivery.dao.IRoleDAO;
import ai.leantech.delivery.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleDAO extends AbstractJpaDAO<Role> implements IRoleDAO {

    public RoleDAO() {
        super();

        setClazz(Role.class);
    }

    @Override
    public Role findByName(String role) {
        return null;
    }
}
