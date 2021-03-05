package ai.leantech.delivery.dao;

import ai.leantech.delivery.model.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderDAO {

    Optional<Order> findOne(long id);
    List<Order> findAll();
    void create(Order order);
    Order update(Order order);
    void delete(Order order);
    void deleteById(long orderId);

}
