package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.controller.model.order.AdminUpdateOrderRequest;
import ai.leantech.delivery.service.AdminOrderService;
import ai.leantech.delivery.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminOrderService adminOrderService;
    private final UserService userService;

    public AdminController(final AdminOrderService adminOrderService, final UserService userService) {
        this.adminOrderService = adminOrderService;
        this.userService = userService;
    }

    @GetMapping("/orders")
    public List<OrderResponse> getAllOrders(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "payment_type", required = false) String paymentType,
            @RequestParam(name = "customer", required = false) Long customerId,
            @RequestParam(name = "courier", required = false) Long courierId,
            @RequestParam(name = "page", defaultValue = "1") Integer offset,
            @RequestParam(name = "size", defaultValue = "25") Integer limit,
            @RequestParam(name = "order", defaultValue = "DESC") String order

    ) {
        return adminOrderService.getOrders(status, paymentType, customerId, courierId, offset, limit, order);
    }

    @GetMapping("/orders/{id}")
    public OrderResponse getOrderById(@PathVariable(name = "id") Long id) {
        return adminOrderService.getOrderById(id);
    }

    @PatchMapping("/orders/{id}")
    public ResponseEntity<Void> editOrderById(@PathVariable(name = "id") Long id, @RequestBody AdminUpdateOrderRequest request) {
        adminOrderService.editOrderById(id, request);
        return ResponseEntity.noContent().build();
    }

}
