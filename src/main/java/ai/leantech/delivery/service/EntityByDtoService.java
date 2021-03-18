package ai.leantech.delivery.service;

import ai.leantech.delivery.controller.model.order.AdminOrderRequest;
import ai.leantech.delivery.controller.model.order.CustomerOrderRequest;
import ai.leantech.delivery.controller.model.order.OrderItemRequest;
import ai.leantech.delivery.model.*;
import ai.leantech.delivery.repository.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntityByDtoService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    public EntityByDtoService(final OrderItemRepository orderItemRepository,
                              final OrderRepository orderRepository,
                              final ProductRepository productRepository,
                              final RoleRepository roleRepository,
                              final UserRepository userRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public Order convertDtoToOrder(AdminOrderRequest dto) {
        Order order = new Order();
        order.setPaymentType(PaymentType.findTypeByName(dto.getPaymentType()));
        order.setAddress(dto.getAddress());

        Long customerId = dto.getCustomerId();
        Long courierId = dto.getCourierId();

        if (customerId != null) {
            User customer = userRepository.findById(customerId).orElseThrow(
                    () -> new EntityNotFoundException(String.format("Customer with id %s not found", customerId))
            );
            order.setClient(customer);
        }
        if (courierId != null) {
            User courier = userRepository.findById(courierId).orElseThrow(
                    () -> new EntityNotFoundException(String.format("Courier with id %s not found", courierId))
            );
            order.setCourier(courier);
        }

        List<OrderItem> items = dto.getItems().stream()
                .map(this::convertOrderItemDtoToItem)
                .collect(Collectors.toList());
        items.forEach(item -> item.setOrder(order));
        order.setOrderItems(items);
        return order;
    }

    public OrderItem convertOrderItemDtoToItem(OrderItemRequest dto) {
        OrderItem item = new OrderItem();
        Long prodId = dto.getProductId();
        Product prod = productRepository.findById(prodId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Product with id %s not found", prodId))
        );
        item.setProduct(prod);
        item.setQuantity(dto.getQuantity());
        return item;
    }

    public Order convertDtoToOrder(CustomerOrderRequest dto) {
        Order order = new Order();
        order.setPaymentType(PaymentType.findTypeByName(dto.getPaymentType()));
        order.setAddress(dto.getAddress());

        List<OrderItem> items = dto.getItems().stream()
                .map(this::convertOrderItemDtoToItem)
                .collect(Collectors.toList());
        items.forEach(item -> item.setOrder(order));
        order.setOrderItems(items);
        return order;
    }
}
