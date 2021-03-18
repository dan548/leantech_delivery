package ai.leantech.delivery.controller.model.order;

import ai.leantech.delivery.model.*;


public class OrderDtoConverter {

    public OrderResponse convertOrderToOrderResp(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .address(order.getAddress())
                .status(order.getStatus())
                .paymentType(order.getPaymentType())
                .updated(order.getUpdatedAt())
                .created(order.getCreatedAt())
                .items(order.getOrderItems())
                .build();
    }
}
