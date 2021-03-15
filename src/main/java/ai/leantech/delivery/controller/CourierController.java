package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.service.CourierOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courier")
public class CourierController {

    private final CourierOrderService courierOrderService;

    public CourierController(final CourierOrderService courierOrderService) {
        this.courierOrderService = courierOrderService;
    }

    @GetMapping("/orders")
    public List<OrderResponse> getAllOrders(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "payment_type", required = false) String paymentType,
            @RequestParam(name = "customer", required = false) Long customerId,
            @RequestParam(name = "page", defaultValue = "1") Integer offset,
            @RequestParam(name = "size", defaultValue = "25") Integer limit,
            @RequestParam(name = "order", defaultValue = "desc") String order

    ) {
        return courierOrderService.getOrders(status, paymentType, customerId, offset, limit, order);
    }
}
