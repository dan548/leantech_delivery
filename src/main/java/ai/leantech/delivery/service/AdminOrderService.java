package ai.leantech.delivery.service;

import ai.leantech.delivery.controller.model.order.AdminOrderRequest;
import ai.leantech.delivery.controller.model.order.OrderDtoConverter;
import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.entity.Order;
import ai.leantech.delivery.model.OrderStatus;
import ai.leantech.delivery.model.PaymentType;
import ai.leantech.delivery.repository.OrderItemRepository;
import ai.leantech.delivery.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static ai.leantech.delivery.model.OrderSpecs.*;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class AdminOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ObjectMapper objectMapper;
    private final OrderDtoConverter orderDtoConverter;

    public AdminOrderService(final OrderRepository orderRepository,
                             final OrderItemRepository orderItemRepository,
                             final ObjectMapper objectMapper,
                             final OrderDtoConverter orderDtoConverter) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.objectMapper = objectMapper;
        this.orderDtoConverter = orderDtoConverter;
    }

    public Order createOrder(AdminOrderRequest request) {
        Order order = orderDtoConverter.convertDtoToOrder(request);
        OffsetDateTime now = OffsetDateTime.now();
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        order.setStatus(OrderStatus.CREATED);
        Order result = orderRepository.save(order);
        order.getOrderItems().forEach(it -> {
            it.setOrder(result);
            orderItemRepository.save(it);
        });
        return result;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrders(String status,
                                         String paymentType,
                                         Long customerId,
                                         Long courierId,
                                         Integer offset,
                                         Integer limit,
                                         String sortDirection) {
        Pageable page = PageRequest.of(offset, limit, Sort.Direction.fromString(sortDirection), "createdAt");
        Specification<Order> spec = hasCustomerWithId(customerId)
                .and(hasCourierWithId(courierId))
                .and(isPaymentType(PaymentType.findTypeByName(paymentType)))
                .and(isStatus(OrderStatus.findStatusByName(status)));
        return orderRepository.findAll(spec, page)
                .get()
                .map(orderDtoConverter::convertOrderToOrderResp)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id).map(orderDtoConverter::convertOrderToOrderResp).orElseThrow(
                () -> new EntityNotFoundException(String.format("Order with id %s not found", id))
        );
    }

    @Transactional(readOnly = true)
    public Optional<Order> getOrderEntityById(Long id) {
        return orderRepository.findById(id);
    }

    public OrderResponse updateOrder(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Order order = getOrderEntityById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Order with id %s not found", id))
        );
        Order newOrder = applyPatchToOrder(patch, order);
        newOrder.setUpdatedAt(OffsetDateTime.now());
        return orderDtoConverter.convertOrderToOrderResp(orderRepository.save(newOrder));
    }

    private Order applyPatchToOrder(JsonPatch patch, Order targetOrder) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetOrder, JsonNode.class));
        return objectMapper.treeToValue(patched, Order.class);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
