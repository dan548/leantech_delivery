package ai.leantech.delivery.controller.model.order;

import lombok.Data;

@Data
public class OrderItemRequest {

    private int quantity;
    private Long productId;

}
