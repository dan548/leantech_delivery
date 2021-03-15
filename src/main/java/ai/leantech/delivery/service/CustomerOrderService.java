package ai.leantech.delivery.service;

import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.model.OrderStatus;
import ai.leantech.delivery.model.PaymentType;
import ai.leantech.delivery.model.User;
import ai.leantech.delivery.repository.OrderRepository;
import ai.leantech.delivery.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

import static ai.leantech.delivery.model.OrderSpecs.*;
import static ai.leantech.delivery.model.OrderSpecs.isStatus;
import static java.util.stream.Collectors.toList;

@Service
public class CustomerOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public CustomerOrderService(final OrderRepository orderRepository, final UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<OrderResponse> getOrders(String status,
                                         String paymentType,
                                         Long courierId,
                                         Integer offset,
                                         Integer limit,
                                         String sortDirection) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        User user = userRepository.findByLogin(login);
        Pageable page = PageRequest.of(offset, limit, Sort.Direction.fromString(sortDirection));
        return orderRepository.findAll(
                hasCustomerWithId(user.getId())
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

}
