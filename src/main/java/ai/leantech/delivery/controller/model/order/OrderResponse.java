package ai.leantech.delivery.controller.model.order;

import ai.leantech.delivery.entity.OrderItem;
import ai.leantech.delivery.model.OrderStatus;
import ai.leantech.delivery.model.PaymentType;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private OffsetDateTime created;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private OffsetDateTime updated;
    private List<OrderItem> items;

}
