package ai.leantech.delivery.service;

import ai.leantech.delivery.controller.model.product.AdminProductRequest;
import ai.leantech.delivery.controller.model.product.ProductDtoConverter;
import ai.leantech.delivery.controller.model.product.ProductResponse;
import ai.leantech.delivery.model.Product;
import ai.leantech.delivery.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository prodRepo;
    private final ProductDtoConverter dtoConverter;

    public ProductService(final ProductRepository prodRepo, final ProductDtoConverter dtoConverter) {
        this.prodRepo = prodRepo;
        this.dtoConverter = dtoConverter;
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
}
