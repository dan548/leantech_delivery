package ai.leantech.delivery.controller.model.order;

import ai.leantech.delivery.model.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDtoConverter {

    public OrderResponse convertOrderToOrderResp(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .address(order.getAddress())
                .status(order.getStatus())
                .paymentType(order.getPaymentType())
                .updated(order.getUpdatedAt())
                .created(order.getCreatedAt())
                .items(order.getOrderItems())
                .build();
    }

    public Order convertDtoToOrder(AdminOrderRequest dto) {
        Order order = new Order();
        order.setPaymentType(PaymentType.findTypeByName(dto.getPaymentType()));
        order.setAddress(dto.getAddress());

        Long customerId = dto.getCustomerId();
        Long courierId = dto.getCourierId();

        if (customerId != null) {
            User customer = new User();
            customer.setId(customerId);
            order.setClient(customer);
        }
        if (courierId != null) {
            User courier = new User();
            courier.setId(courierId);
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
        Product prod = new Product();
        prod.setId(prodId);
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
