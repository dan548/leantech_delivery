package ai.leantech.delivery.repository;

import ai.leantech.delivery.entity.OrderItem;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface OrderItemRepository extends JpaRepositoryImplementation<OrderItem, Long> {



}
