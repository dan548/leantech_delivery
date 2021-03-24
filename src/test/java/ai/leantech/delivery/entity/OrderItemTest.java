package ai.leantech.delivery.entity;

import ai.leantech.delivery.MyJpaTest;
import ai.leantech.delivery.objectmother.OrderItemMother;
import ai.leantech.delivery.objectmother.OrderMother;
import ai.leantech.delivery.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@MyJpaTest
public class OrderItemTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void testEquals() {
        orderItemRepository.save(OrderItemMother.noOrderBuilder().build());
        orderItemRepository.save(OrderItemMother.noOrderBuilder().build());
        OrderItem o1 = new OrderItem();
        OrderItem o2 = new OrderItem();
        assertNotEquals(o2, o1);
        o1 = entityManager.find(OrderItem.class, 1L);
        o2 = entityManager.find(OrderItem.class, 2L);
        assertNotEquals(o2, o1);
        o1 = entityManager.find(OrderItem.class, 1L);
        o2 = entityManager.find(OrderItem.class, 1L);
        assertEquals(o2, o1);
        entityManager.detach(o1);
        assertEquals(o2, o1);
        o1 = entityManager.merge(o1);
        assertEquals(o2, o1);
    }

}
