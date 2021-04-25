package ai.leantech.delivery.entity;

import ai.leantech.delivery.MyJpaTest;
import ai.leantech.delivery.objectmother.ProductMother;
import ai.leantech.delivery.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@MyJpaTest
public class ProductTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testEquals() {
        productRepository.save(ProductMother.complete().build());
        productRepository.save(ProductMother.complete().build());
        Product o1 = new Product();
        Product o2 = new Product();
        assertNotEquals(o2, o1);
        o1 = entityManager.find(Product.class, 1L);
        o2 = entityManager.find(Product.class, 2L);
        assertNotEquals(o2, o1);
        o1 = entityManager.find(Product.class, 1L);
        o2 = entityManager.find(Product.class, 1L);
        assertEquals(o2, o1);
        entityManager.detach(o1);
        assertEquals(o2, o1);
        o1 = entityManager.merge(o1);
        assertEquals(o2, o1);
    }

}
