package ai.leantech.delivery.service;

import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.model.OrderSpecs;
import ai.leantech.delivery.model.OrderStatus;
import ai.leantech.delivery.model.PaymentType;
import ai.leantech.delivery.model.User;
import ai.leantech.delivery.repository.OrderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CustomerOrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public CustomerOrderService(final OrderRepository orderRepository, final UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public List<OrderResponse> getOrders(String status,
                                         String paymentType,
                                         Long courierId,
                                         Integer offset,
                                         Integer limit,
                                         String sortDirection) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByAuthentication(auth);
        Pageable page = PageRequest.of(offset, limit, Sort.Direction.fromString(sortDirection));
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

}
