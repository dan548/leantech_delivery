package ai.leantech.delivery.objectmother;

import ai.leantech.delivery.entity.Order;
import ai.leantech.delivery.entity.OrderItem;

public class OrderItemMother {

    static OrderItem.OrderItemBuilder complete(Order order) {
        return OrderItem.builder()
                .quantity(4)
                .order(order);
    }

    public static OrderItem.OrderItemBuilder noOrderBuilder() {
        return OrderItem.builder()
                .quantity(4);
    }

}
