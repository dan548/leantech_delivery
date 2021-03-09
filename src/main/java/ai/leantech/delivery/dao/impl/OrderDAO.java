package ai.leantech.delivery.dao.impl;

import ai.leantech.delivery.dao.AbstractJpaDAO;
import ai.leantech.delivery.dao.IOrderDAO;
import ai.leantech.delivery.model.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Repository
@Transactional
public class OrderDAO extends AbstractJpaDAO<Order> implements IOrderDAO {

    public OrderDAO() {
        super();

        setClazz(Order.class);
    }

    @Override
    public void create(Order order) {
        OffsetDateTime now = OffsetDateTime.now();

        order.setCreatedAt(now);
        order.setUpdatedAt(now);

        super.create(order);
    }

}
