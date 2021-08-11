package ai.leantech.delivery;

import ai.leantech.delivery.entity.Product;
import ai.leantech.delivery.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class DeliveryApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:11.1")
            .withUsername("daniilp")
            .withPassword("password")
            .withDatabaseName("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Test
    void contextLoads() {

        Product product = new Product();
        product.setName("bread");
        product.setPrice(228);

        productRepository.save(product);

        System.out.println("Context loads!");
    }

}
