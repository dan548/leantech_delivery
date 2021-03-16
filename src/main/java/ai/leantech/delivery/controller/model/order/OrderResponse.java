package ai.leantech.delivery.controller.model.order;

import ai.leantech.delivery.model.OrderItem;
import ai.leantech.delivery.model.OrderStatus;
import ai.leantech.delivery.model.PaymentType;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponse {

    private Long id;
    private PaymentType paymentType;
    private OrderStatus status;
    private String address;
    private OffsetDateTime created;
    private OffsetDateTime updated;
    private List<OrderItem> items;

}