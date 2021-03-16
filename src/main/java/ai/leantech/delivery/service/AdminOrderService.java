package ai.leantech.delivery.service;

import ai.leantech.delivery.controller.model.order.AdminOrderRequest;
import ai.leantech.delivery.controller.model.order.OrderDtoConverter;
import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.model.Order;
import ai.leantech.delivery.model.OrderStatus;
import ai.leantech.delivery.model.PaymentType;
import ai.leantech.delivery.repository.OrderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static ai.leantech.delivery.model.OrderSpecs.*;
import static java.util.stream.Collectors.toList;

@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;

    public AdminOrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(AdminOrderRequest request) {
        Order order = OrderDtoConverter.convertDtoToOrder(request);
        OffsetDateTime now = OffsetDateTime.now();
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        order.setStatus(OrderStatus.CREATED);
        return orderRepository.save(order);
    }

    public List<OrderResponse> getOrders(String status,
                                         String paymentType,
                                         Long customerId,
                                         Long courierId,
                                         Integer offset,
                                         Integer limit,
                                         String sortDirection) {
        Pageable page = PageRequest.of(offset, limit, Sort.Direction.fromString(sortDirection), "createdAt");
        return orderRepository.findAll(
                hasCustomerWithId(customerId)
                        .and(hasCourierWithId(courierId))
                        .and(isPaymentType(PaymentType.findTypeByName(paymentType)))
                        .and(isStatus(OrderStatus.findStatusByName(status))),
                page)
                .get()
                .map(o -> OrderResponse.builder()
                        .address(o.getAddress())
                        .id(o.getId())
                        .paymentType(o.getPaymentType())
                        .created(o.getCreatedAt())
                        .updated(o.getUpdatedAt())
                        .status(o.getStatus())
                        .items(o.getOrderItems())
                        .build())
                .collect(toList());
    }

    public Optional<OrderResponse> getOrderById(Long id) {
        return orderRepository.findById(id).map(OrderDtoConverter::convertOrderToOrderResp);
    }

    public Optional<Order> getOrderEntityById(Long id) {
        return orderRepository.findById(id);
    }

    public void updateOrder(Order newOrder) {
        newOrder.setUpdatedAt(OffsetDateTime.now());
        orderRepository.save(newOrder);
    }
}
