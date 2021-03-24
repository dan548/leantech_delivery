package ai.leantech.delivery.repository;

import ai.leantech.delivery.entity.Order;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepositoryImplementation<Order, Long> {

}
