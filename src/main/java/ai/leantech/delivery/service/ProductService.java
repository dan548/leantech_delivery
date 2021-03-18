package ai.leantech.delivery.service;

import ai.leantech.delivery.controller.model.product.AdminProductRequest;
import ai.leantech.delivery.controller.model.product.ProductDtoConverter;
import ai.leantech.delivery.controller.model.product.ProductResponse;
import ai.leantech.delivery.model.Order;
import ai.leantech.delivery.model.Product;
import ai.leantech.delivery.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository prodRepo;
    private final ProductDtoConverter dtoConverter;
    private final ObjectMapper objectMapper;

    public ProductService(final ProductRepository prodRepo, final ProductDtoConverter dtoConverter, final ObjectMapper objectMapper) {
        this.prodRepo = prodRepo;
        this.dtoConverter = dtoConverter;
        this.objectMapper = objectMapper;
    }

    public List<ProductResponse> getProducts() {
        return prodRepo.findAll().stream()
                .map(dtoConverter::convertProductToProductResp)
                .collect(Collectors.toList());
    }

    public Product createProduct(AdminProductRequest request) {
        Product product = dtoConverter.convertDtoToProduct(request);
        return prodRepo.save(product);
    }

    public ProductResponse getProductById(Long id) {
        return prodRepo.findById(id).map(dtoConverter::convertProductToProductResp).orElseThrow(
                () -> new EntityNotFoundException(String.format("Product with id %s not found", id))
        );
    }

    public ProductResponse updateProduct(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Product product = prodRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Product with id %s not found", id))
        );
        Product newProduct = applyPatchToProduct(patch, product);
        return dtoConverter.convertProductToProductResp(prodRepo.save(newProduct));
    }

    private Product applyPatchToProduct(JsonPatch patch, Product targetProduct) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetProduct, JsonNode.class));
        return objectMapper.treeToValue(patched, Product.class);
    }

    public void deleteProduct(Long id) {
        prodRepo.deleteById(id);
    }
}
