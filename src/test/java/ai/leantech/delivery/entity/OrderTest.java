package ai.leantech.delivery.entity;

import ai.leantech.delivery.MyJpaTest;
import ai.leantech.delivery.objectmother.OrderMother;
import ai.leantech.delivery.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@MyJpaTest
public class OrderTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testEquals() {
        orderRepository.save(OrderMother.orderNoItemsNoClientBuilder().build());
        orderRepository.save(OrderMother.orderNoItemsNoClientBuilder().build());
        Order o1 = new Order();
        Order o2 = new Order();
        assertNotEquals(o2, o1);
        o1 = entityManager.find(Order.class, 1L);
        o2 = entityManager.find(Order.class, 2L);
        assertNotEquals(o2, o1);
        o1 = entityManager.find(Order.class, 1L);
        o2 = entityManager.find(Order.class, 1L);
        assertEquals(o2, o1);
        entityManager.detach(o1);
        assertEquals(o2, o1);
        o1 = entityManager.merge(o1);
        assertEquals(o2, o1);
    }

}
