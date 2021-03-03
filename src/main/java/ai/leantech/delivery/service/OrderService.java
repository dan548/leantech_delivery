package ai.leantech.delivery.service;

import ai.leantech.delivery.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    public List<Order> getOrders(String status, String order, Integer page, Integer size) {
        return new ArrayList<>();
    }

}
