package ai.leantech.delivery.controller.model.product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

    private long id;
    private String name;
    private int price;

}
