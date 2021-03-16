package ai.leantech.delivery.controller.model.order;

import lombok.Data;

import java.util.List;

@Data
public class AdminOrderRequest {

    private String paymentType;
    private Long courierId;
    private Long customerId;
    private String address;
    private List<OrderItemRequest> items;

}
