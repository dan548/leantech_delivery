package ai.leantech.delivery.controller.model.order;

import lombok.Data;

import java.util.List;

@Data
public class CustomerOrderRequest {

    private String paymentType;
    private String address;
    private List<OrderItemRequest> items;

}
