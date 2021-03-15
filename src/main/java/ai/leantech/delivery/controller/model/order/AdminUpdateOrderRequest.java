package ai.leantech.delivery.controller.model.order;

import lombok.Data;

@Data
public class AdminUpdateOrderRequest {

    private String paymentType;
    private String orderStatus;
    private String courierId;
    private String customerId;
    private String address;

}
