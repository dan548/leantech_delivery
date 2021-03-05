package ai.leantech.delivery.daoimpl;

import ai.leantech.delivery.dao.AbstractJpaDAO;
import ai.leantech.delivery.dao.IOrderDAO;
import ai.leantech.delivery.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAO extends AbstractJpaDAO<Order> implements IOrderDAO {

    public OrderDAO() {
        super();

        setClazz(Order.class);
    }

    @Override
    public void create(Order order) {

        super.create(order);
    }

}
