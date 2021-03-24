package ai.leantech.delivery.objectmother;

import ai.leantech.delivery.entity.Product;

public class ProductMother {

    public static Product.ProductBuilder complete() {
        return Product.builder()
                .name("milk")
                .price(4000);
    }

}
