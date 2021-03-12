package ai.leantech.delivery.service;

import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.model.Order;
import ai.leantech.delivery.repository.OrderRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderResponse> getOrders(String status,
                                         String paymentType,
                                         Long customerId,
                                         Long courierId,
                                         Integer offset,
                                         Integer limit,
                                         String sort) {
        Pageable page = PageRequest.of(offset, limit);
        Example<Order> example = Example.of(new Order());
        return orderRepository.findAll(example, page).get()
                .map(order -> OrderResponse.builder().build())
                .collect(toList());
    }
}
