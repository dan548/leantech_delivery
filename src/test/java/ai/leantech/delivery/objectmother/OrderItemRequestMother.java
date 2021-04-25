package ai.leantech.delivery.objectmother;

import ai.leantech.delivery.controller.model.order.OrderItemRequest;

public class OrderItemRequestMother {

    public static OrderItemRequest.OrderItemRequestBuilder complete() {
        return OrderItemRequest.builder()
                .productId(4L)
                .quantity(2);
    }

}
