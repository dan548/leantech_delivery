package ai.leantech.delivery.service;

import ai.leantech.delivery.controller.model.order.AdminUpdateOrderRequest;
import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.exception.NotFoundException;
import ai.leantech.delivery.model.Order;
import ai.leantech.delivery.model.OrderStatus;
import ai.leantech.delivery.model.PaymentType;
import ai.leantech.delivery.repository.OrderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
                        .build())
                .collect(toList());
    }

    public OrderResponse getOrderById(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);

        if (orderOpt.isEmpty()) {
            return OrderResponse.builder().build();
        }

        Order order = orderOpt.get();

        return OrderResponse.builder()
                .id(id)
                .address(order.getAddress())
                .status(order.getStatus())
                .paymentType(order.getPaymentType())
                .updated(order.getUpdatedAt())
                .created(order.getCreatedAt())
                .build();
    }


    public void editOrderById(Long id, AdminUpdateOrderRequest request) {
        //TODO successful edit scenario
        throw new NotFoundException();
    }
}
