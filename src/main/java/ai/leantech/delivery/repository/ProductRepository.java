package ai.leantech.delivery.repository;

import ai.leantech.delivery.entity.Product;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface ProductRepository extends JpaRepositoryImplementation<Product, Long> {



}
