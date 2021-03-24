package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.product.AdminProductRequest;
import ai.leantech.delivery.controller.model.product.ProductResponse;
import ai.leantech.delivery.entity.Product;
import ai.leantech.delivery.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<ProductResponse> getAllProducts() {
        return productService.getProducts();
    }

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody AdminProductRequest request) {
        Product createdProduct = productService.createProduct(request);
        URI location = UriComponentsBuilder.fromPath("/api/admin/products/")
                .path(String.valueOf(createdProduct.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ProductResponse editProductById(@PathVariable Long id, @RequestBody JsonPatch patch)
            throws JsonPatchException, JsonProcessingException {
        return productService.updateProduct(id, patch);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
