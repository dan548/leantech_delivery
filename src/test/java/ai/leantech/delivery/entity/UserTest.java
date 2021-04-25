package ai.leantech.delivery.entity;

import ai.leantech.delivery.MyJpaTest;
import ai.leantech.delivery.objectmother.UserMother;
import ai.leantech.delivery.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@MyJpaTest
public class UserTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testEquals() {
        userRepository.save(UserMother.userNoRoleBuilder().build());
        userRepository.save(UserMother.userNoRoleBuilder().build());
        User o1 = new User();
        User o2 = new User();
        assertNotEquals(o2, o1);
        o1 = entityManager.find(User.class, 1L);
        o2 = entityManager.find(User.class, 2L);
        assertNotEquals(o2, o1);
        o1 = entityManager.find(User.class, 1L);
        o2 = entityManager.find(User.class, 1L);
        assertEquals(o2, o1);
        entityManager.detach(o1);
        assertEquals(o2, o1);
        o1 = entityManager.merge(o1);
        assertEquals(o2, o1);
    }

}
