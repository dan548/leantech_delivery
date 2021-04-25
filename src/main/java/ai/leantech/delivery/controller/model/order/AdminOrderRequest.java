package ai.leantech.delivery.controller.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminOrderRequest {

    private String paymentType;
    private Long courierId;
    private Long customerId;
    private String address;
    private List<OrderItemRequest> items;

}
