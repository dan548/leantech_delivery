package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.order.AdminOrderRequest;
import ai.leantech.delivery.controller.model.order.CustomerOrderRequest;
import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.model.Order;
import ai.leantech.delivery.service.CustomerOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customer/orders")
public class CustomerController {

    private final CustomerOrderService customerOrderService;

    public CustomerController(final CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @GetMapping()
    public List<OrderResponse> getAllOrders(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "payment_type", required = false) String paymentType,
            @RequestParam(name = "courier", required = false) Long courierId,
            @RequestParam(name = "page", defaultValue = "1") Integer offset,
            @RequestParam(name = "size", defaultValue = "25") Integer limit,
            @RequestParam(name = "order", defaultValue = "desc") String order

    ) {
        return customerOrderService.getOrders(status, paymentType, courierId, offset, limit, order);
    }

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody CustomerOrderRequest request) {
        Order createdOrder = customerOrderService.createOrder(request);
        URI location = UriComponentsBuilder.fromPath("/api/customer/orders/")
                .path(String.valueOf(createdOrder.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return customerOrderService.getOrderById(id);
    }

}
