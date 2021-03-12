package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.service.OrderService;
import ai.leantech.delivery.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final OrderService orderService;
    private final UserService userService;

    public AdminController(final OrderService orderService, final UserService userService) {
        this.orderService = orderService;
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
            @RequestParam(name = "order", defaultValue = "desc") String order

    ) {
        return orderService.getOrders(status, paymentType, customerId, courierId, offset, limit, order);
    }

}
