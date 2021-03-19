package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.order.CustomerOrderRequest;
import ai.leantech.delivery.controller.model.order.OrderResponse;
import ai.leantech.delivery.controller.model.product.ProductResponse;
import ai.leantech.delivery.model.Order;
import ai.leantech.delivery.service.CustomerOrderService;
import ai.leantech.delivery.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerOrderService customerOrderService;
    private final ProductService productService;

    public CustomerController(final CustomerOrderService customerOrderService,
                              final ProductService productService) {
        this.customerOrderService = customerOrderService;
        this.productService = productService;
    }

    @GetMapping("/orders")
    public List<OrderResponse> getAllOrders(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "payment_type", required = false) String paymentType,
            @RequestParam(name = "courier", required = false) Long courierId,
            @RequestParam(name = "page", defaultValue = "0") Integer offset,
            @RequestParam(name = "size", defaultValue = "25") Integer limit,
            @RequestParam(name = "order", defaultValue = "desc") String order

    ) {
        return customerOrderService.getOrders(status, paymentType, courierId, offset, limit, order);
    }

    @PostMapping("/orders")
    public ResponseEntity<Void> create(@RequestBody CustomerOrderRequest request) {
        Order createdOrder = customerOrderService.createOrder(request);
        URI location = UriComponentsBuilder.fromPath("/api/customer/orders/")
                .path(String.valueOf(createdOrder.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/orders/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return customerOrderService.getOrderById(id);
    }

    @GetMapping("/products")
    public List<ProductResponse> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/products/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
}
