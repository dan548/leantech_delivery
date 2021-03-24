package ai.leantech.delivery.objectmother;

import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.entity.OrderItem;
import ai.leantech.delivery.model.OrderStatus;
import ai.leantech.delivery.model.PaymentType;

import java.time.OffsetDateTime;
import java.util.Collections;

public class OrderResponseMother {

    public static OrderResponse.OrderResponseBuilder complete() {
        return OrderResponse.builder()
                .id(666L)
                .status(OrderStatus.CREATED)
                .address("Omsk, ul. Pushkina, dom Kolotushkina")
                .paymentType(PaymentType.CASH)
                .items(Collections.singletonList(new OrderItem()))
                .created(OffsetDateTime.now())
                .updated(OffsetDateTime.now());

    }

}
