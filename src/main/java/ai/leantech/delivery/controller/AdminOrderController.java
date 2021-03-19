package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.order.AdminOrderRequest;
import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.model.Order;
import ai.leantech.delivery.service.AdminOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(final AdminOrderService adminOrderService) {
        this.adminOrderService = adminOrderService;
    }

    @GetMapping()
    public List<OrderResponse> getAllOrders(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "payment_type", required = false) String paymentType,
            @RequestParam(name = "customer", required = false) Long customerId,
            @RequestParam(name = "courier", required = false) Long courierId,
            @RequestParam(name = "page", defaultValue = "0") Integer offset,
            @RequestParam(name = "size", defaultValue = "25") Integer limit,
            @RequestParam(name = "order", defaultValue = "DESC") String order

    ) {
        return adminOrderService.getOrders(status, paymentType, customerId, courierId, offset, limit, order);
    }

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody AdminOrderRequest request) {
        Order createdOrder = adminOrderService.createOrder(request);
        URI location = UriComponentsBuilder.fromPath("/api/admin/orders/")
                .path(String.valueOf(createdOrder.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return adminOrderService.getOrderById(id);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public OrderResponse editOrderById(@PathVariable Long id, @RequestBody JsonPatch patch)
            throws JsonPatchException, JsonProcessingException {
        return adminOrderService.updateOrder(id, patch);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteOrderById(@PathVariable Long id) {
        adminOrderService.deleteOrder(id);
    }

}
