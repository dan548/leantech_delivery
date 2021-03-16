package ai.leantech.delivery.controller.model.product;

import ai.leantech.delivery.model.Product;

public class ProductDtoConverter {

    public static ProductResponse convertProductToProductResp(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    public static Product convertDtoToProduct(AdminProductRequest dto) {
        Product product = new Product();
        product.setPrice(dto.getPrice());
        product.setName(dto.getName());
        return product;
    }
}
