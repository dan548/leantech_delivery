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

import javax.persistence.EntityNotFoundException;
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
            @RequestParam(name = "page", defaultValue = "1") Integer offset,
            @RequestParam(name = "size", defaultValue = "25") Integer limit,
            @RequestParam(name = "order", defaultValue = "DESC") String order

    ) {
        return adminOrderService.getOrders(status, paymentType, customerId, courierId, offset, limit, order);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return adminOrderService.getOrderById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Order with id %s not found", id))
        );
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

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public OrderResponse editOrderById(@PathVariable Long id, @RequestBody JsonPatch patch)
            throws JsonPatchException, JsonProcessingException {
        Order order = adminOrderService.getOrderEntityById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Order with id %s not found", id))
        );
        return adminOrderService.updateOrder(order, patch);
    }

}
