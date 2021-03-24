package ai.leantech.delivery.entity;

import ai.leantech.delivery.MyJpaTest;
import ai.leantech.delivery.objectmother.RoleMother;
import ai.leantech.delivery.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@MyJpaTest
public class RoleTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testEquals() {
        roleRepository.save(RoleMother.admin().build());
        roleRepository.save(RoleMother.admin().build());
        Role o1 = new Role();
        Role o2 = new Role();
        assertNotEquals(o2, o1);
        o1 = entityManager.find(Role.class, 1L);
        o2 = entityManager.find(Role.class, 2L);
        assertNotEquals(o2, o1);
        o1 = entityManager.find(Role.class, 1L);
        o2 = entityManager.find(Role.class, 1L);
        assertEquals(o2, o1);
        entityManager.detach(o1);
        assertEquals(o2, o1);
        o1 = entityManager.merge(o1);
        assertEquals(o2, o1);
    }

}
