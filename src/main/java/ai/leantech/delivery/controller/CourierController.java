package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.exception.NoSuchOrderStatusException;
import ai.leantech.delivery.service.CourierOrderService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return courierOrderService.getOrderById(id);
    }

    @PatchMapping("/{id}/status")
    public String editOrderStatus(@PathVariable Long id, @RequestBody String status) throws NoSuchOrderStatusException {
        courierOrderService.editOrderStatus(id, status);
        return status;
    }
}
