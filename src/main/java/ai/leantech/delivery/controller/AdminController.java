package ai.leantech.delivery.controller;

import ai.leantech.delivery.model.Order;
import ai.leantech.delivery.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final OrderService service;

    public AdminController(final OrderService service) {
        this.service = service;
    }

    @RequestMapping(value = "/orders", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Order>> getAllOrders(
            final @RequestParam(name = "status", defaultValue = "done") String status,
            final @RequestParam(name = "order", defaultValue = "desc") String order,
            final @RequestParam(name = "page", defaultValue = "1") Integer page,
            final @RequestParam(name = "size", defaultValue = "25") Integer size
    ) {
        return ResponseEntity.ok(service.getOrders(status, order, page, size));
    }



}
