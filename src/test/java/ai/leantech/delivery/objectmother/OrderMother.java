package ai.leantech.delivery.objectmother;

import ai.leantech.delivery.entity.Order;
import ai.leantech.delivery.model.OrderStatus;
import ai.leantech.delivery.model.PaymentType;

import java.time.OffsetDateTime;
import java.util.List;

public class OrderMother {

    public static Order.OrderBuilder orderNoItemsNoClientBuilder() {
        return Order.builder()
                .address("Omsk, ul. Lenina, dom 13")
                .paymentType(PaymentType.CASH)
                .status(OrderStatus.CREATED)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now());
    }

    public static Order completeOrder() {
        Order order = orderNoItemsNoClientBuilder().build();
        order.setOrderItems(List.of(OrderItemMother.complete(order).build()));
        order.setClient(UserMother.completeClient());
        return order;
    }

}
