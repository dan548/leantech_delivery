package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.order.AdminOrderRequest;
import ai.leantech.delivery.controller.model.order.OrderDtoConverter;
import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.model.Order;
import ai.leantech.delivery.service.AdminOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;
    private final ObjectMapper objectMapper;

    public AdminOrderController(final AdminOrderService adminOrderService, final ObjectMapper objectMapper) {
        this.adminOrderService = adminOrderService;
        this.objectMapper = objectMapper;
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
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
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
    public ResponseEntity<OrderResponse> editOrderById(@PathVariable Long id, @RequestBody JsonPatch patch) {
        try {
            Order order = adminOrderService.getOrderEntityById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
            );
            Order orderPatched = applyPatchToOrder(patch, order);
            adminOrderService.updateOrder(orderPatched);
            return ResponseEntity.ok(OrderDtoConverter.convertOrderToOrderResp(orderPatched));
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Order applyPatchToOrder(JsonPatch patch, Order targetOrder) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetOrder, JsonNode.class));
        return objectMapper.treeToValue(patched, Order.class);
    }

}
