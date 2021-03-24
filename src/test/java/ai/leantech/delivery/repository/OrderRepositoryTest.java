package ai.leantech.delivery.repository;

import ai.leantech.delivery.MyJpaTest;
import ai.leantech.delivery.entity.Order;
import ai.leantech.delivery.objectmother.OrderMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.util.Arrays;

import static ai.leantech.delivery.model.OrderAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MyJpaTest
public class OrderRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void injectedComponentsAreNotNull(){
        org.assertj.core.api.Assertions.assertThat(dataSource).isNotNull();
        org.assertj.core.api.Assertions.assertThat(jdbcTemplate).isNotNull();
        org.assertj.core.api.Assertions.assertThat(entityManager).isNotNull();
        org.assertj.core.api.Assertions.assertThat(orderRepository).isNotNull();
    }

    @Test
    void whenSaved_thenFindsById() {
        Order ord = OrderMother.completeOrder();
        ord = orderRepository.save(ord);
        assertThat(ord).hasId();
        assertTrue(orderRepository.findById(ord.getId()).isPresent());
        assertEquals(ord, orderRepository.findById(ord.getId()).get());
    }

    @Test
    void whenDeleted_thenNoOrderWithSuchId() {
        Order ord = OrderMother.completeOrder();
        ord = orderRepository.save(ord);
        orderRepository.deleteById(ord.getId());
        assertTrue(orderRepository.findById(ord.getId()).isEmpty());
    }
}
