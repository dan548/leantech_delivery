package ai.leantech.delivery.controller.model.order;

import ai.leantech.delivery.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDtoConverter {

    public static OrderResponse convertOrderToOrderResp(Order order) {
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

    public static Order convertDtoToOrder(AdminOrderRequest dto) {
        Order order = new Order();
        order.setPaymentType(PaymentType.findTypeByName(dto.getPaymentType()));
        order.setAddress(dto.getAddress());

        if (dto.getCustomerId() != null) {
            User customer = new User();
            customer.setId(dto.getCustomerId());
            order.setClient(customer);
        }
        if (dto.getCourierId() != null) {
            User courier = new User();
            courier.setId(dto.getCourierId());
            order.setCourier(courier);
        }

        List<OrderItem> items = dto.getItems().stream()
                .map(OrderDtoConverter::convertOrderItemDtoToItem)
                .collect(Collectors.toList());
        items.forEach(item -> item.setOrder(order));
        order.setOrderItems(items);
        return order;
    }

    public static OrderItem convertOrderItemDtoToItem(OrderItemRequest dto) {
        OrderItem item = new OrderItem();
        Product prod = new Product();
        prod.setId(dto.getProductId());
        item.setProduct(prod);
        item.setQuantity(dto.getQuantity());
        return item;
    }
}
