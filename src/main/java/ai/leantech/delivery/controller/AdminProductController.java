package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.product.AdminProductRequest;
import ai.leantech.delivery.controller.model.product.ProductResponse;
import ai.leantech.delivery.model.Product;
import ai.leantech.delivery.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private final ProductService productService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AdminProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<ProductResponse> getAllProducts() {
        return productService.getProducts();
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody AdminProductRequest request) {
        Product createdProduct = productService.createProduct(request);
        URI location = UriComponentsBuilder.fromPath("/api/admin/products/")
                .path(String.valueOf(createdProduct.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
