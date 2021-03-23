package ai.leantech.delivery.objectmother;

import ai.leantech.delivery.controller.model.order.AdminOrderRequest;

import java.util.Arrays;

public class AdminOrderRequestMother {

    public static AdminOrderRequest.AdminOrderRequestBuilder complete() {
        return AdminOrderRequest.builder()
                .address("ul. Pushkina, dom Kolotushkina")
                .courierId(4L)
                .customerId(4L)
                .items(Arrays.asList(OrderItemRequestMother.complete().build(),
                        OrderItemRequestMother.complete()
                .quantity(7).build()))
                .paymentType("cash");
    }

}
