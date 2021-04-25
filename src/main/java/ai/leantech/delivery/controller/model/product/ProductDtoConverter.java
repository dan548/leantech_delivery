package ai.leantech.delivery.controller.model.product;

import ai.leantech.delivery.entity.Product;

public class ProductDtoConverter {

    public ProductResponse convertProductToProductResp(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    public Product convertDtoToProduct(AdminProductRequest dto) {
        Product product = new Product();
        product.setPrice(dto.getPrice());
        product.setName(dto.getName());
        return product;
    }
}
