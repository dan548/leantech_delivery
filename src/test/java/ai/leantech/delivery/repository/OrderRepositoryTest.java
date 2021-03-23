package ai.leantech.delivery.repository;

import ai.leantech.delivery.MyJpaTest;
import ai.leantech.delivery.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static ai.leantech.delivery.model.OrderAssert.assertThat;

@MyJpaTest
public class OrderRepositoryTest {

    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private OrderRepository orderRepository;

    @Test
    void injectedComponentsAreNotNull(){
        org.assertj.core.api.Assertions.assertThat(dataSource).isNotNull();
        org.assertj.core.api.Assertions.assertThat(jdbcTemplate).isNotNull();
        org.assertj.core.api.Assertions.assertThat(entityManager).isNotNull();
        org.assertj.core.api.Assertions.assertThat(orderRepository).isNotNull();
    }

    @Test
    void isOrderSavedWithId() {
        Order ord = new Order();
        ord = orderRepository.save(ord);
        assertThat(ord).hasId();
    }

}
