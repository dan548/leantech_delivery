package ai.leantech.delivery.service;

import ai.leantech.delivery.controller.model.order.OrderDtoConverter;
import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.entity.Order;
import ai.leantech.delivery.entity.User;
import ai.leantech.delivery.exception.NoSuchOrderStatusException;
import ai.leantech.delivery.model.*;
import ai.leantech.delivery.repository.OrderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static ai.leantech.delivery.model.OrderSpecs.*;
import static ai.leantech.delivery.model.OrderSpecs.isStatus;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class CourierOrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderDtoConverter orderDtoConverter;

    public CourierOrderService(final OrderRepository orderRepository,
                               final UserService userService,
                               final OrderDtoConverter orderDtoConverter) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.orderDtoConverter = orderDtoConverter;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrders(String status,
                                         String paymentType,
                                         Long customerId,
                                         Integer offset,
                                         Integer limit,
                                         String sortDirection) {
        User user = userService.getUserByAuthentication();
        Pageable page = PageRequest.of(offset, limit, Sort.Direction.fromString(sortDirection), "createdAt");
        Specification<Order> spec = OrderSpecs.hasCustomerWithId(customerId)
                .and(OrderSpecs.hasCourierWithId(user.getId()))
                .and(OrderSpecs.isPaymentType(PaymentType.findTypeByName(paymentType)))
                .and(OrderSpecs.isStatus(OrderStatus.findStatusByName(status)));
        return orderRepository.findAll(spec, page)
                .get()
                .map(orderDtoConverter::convertOrderToOrderResp)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        return getOptionalOrderEntityById(id).map(orderDtoConverter::convertOrderToOrderResp).orElseThrow(
                () -> new EntityNotFoundException(String.format("Order with id %s not found", id))
        );
    }

    public void editOrderStatus(Long id, String status) throws NoSuchOrderStatusException {
        OrderStatus statusType = Optional.ofNullable(OrderStatus.findStatusByName(status)).orElseThrow(
                () -> new NoSuchOrderStatusException(String.format("Status %s not found", status))
        );
        Order o = getOptionalOrderEntityById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Order with id %s not found", id))
        );
        o.setStatus(statusType);
        orderRepository.save(o);
    }

    private Optional<Order> getOptionalOrderEntityById(Long id) {
        User user = userService.getUserByAuthentication();
        return orderRepository.findById(id).filter(order -> order.getCourier().equals(user));
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getVacantOrders(String paymentType, Integer offset, Integer limit, String sortDirection) {
        Pageable page = PageRequest.of(offset, limit, Sort.Direction.fromString(sortDirection), "createdAt");
        Specification<Order> spec = hasNullCourier()
                .and(isPaymentType(PaymentType.findTypeByName(paymentType)))
                .and(isStatus(OrderStatus.CREATED));
        return orderRepository.findAll(spec, page)
                .get()
                .map(orderDtoConverter::convertOrderToOrderResp)
                .collect(toList());
    }
}
