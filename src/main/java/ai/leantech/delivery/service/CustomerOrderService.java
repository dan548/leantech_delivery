package ai.leantech.delivery.service;

import ai.leantech.delivery.controller.model.order.CustomerOrderRequest;
import ai.leantech.delivery.controller.model.order.OrderDtoConverter;
import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.entity.Order;
import ai.leantech.delivery.entity.User;
import ai.leantech.delivery.model.*;
import ai.leantech.delivery.repository.OrderItemRepository;
import ai.leantech.delivery.repository.OrderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class CustomerOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final OrderDtoConverter orderDtoConverter;

    public CustomerOrderService(final OrderRepository orderRepository,
                                final OrderItemRepository orderItemRepository,
                                final UserService userService,
                                final OrderDtoConverter orderDtoConverter) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
        this.orderDtoConverter = orderDtoConverter;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrders(String status,
                                         String paymentType,
                                         Long courierId,
                                         Integer offset,
                                         Integer limit,
                                         String sortDirection) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByAuthentication(auth);
        Pageable page = PageRequest.of(offset, limit, Sort.Direction.fromString(sortDirection), "createdAt");
        return orderRepository.findAll(
                OrderSpecs.hasCustomerWithId(user.getId())
                        .and(OrderSpecs.hasCourierWithId(courierId))
                        .and(OrderSpecs.isPaymentType(PaymentType.findTypeByName(paymentType)))
                        .and(OrderSpecs.isStatus(OrderStatus.findStatusByName(status))),
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

    public Order createOrder(CustomerOrderRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByAuthentication(auth);
        Order order = orderDtoConverter.convertDtoToOrder(request);
        OffsetDateTime now = OffsetDateTime.now();
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        order.setStatus(OrderStatus.CREATED);
        order.setClient(user);
        Order result = orderRepository.save(order);
        order.getOrderItems().forEach(it -> {
            it.setOrder(result);
            orderItemRepository.save(it);
        });
        return result;
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id).map(orderDtoConverter::convertOrderToOrderResp).orElseThrow(
                () -> new EntityNotFoundException(String.format("Order with id %s not found", id))
        );
    }
}
