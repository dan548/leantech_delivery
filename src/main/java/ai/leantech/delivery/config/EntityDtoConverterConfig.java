package ai.leantech.delivery.config;

import ai.leantech.delivery.controller.model.order.OrderDtoConverter;
import ai.leantech.delivery.controller.model.product.ProductDtoConverter;
import ai.leantech.delivery.controller.model.user.UserDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityDtoConverterConfig {

    @Bean
    public OrderDtoConverter orderDtoConverter() {
        return new OrderDtoConverter();
    }

    @Bean
    public ProductDtoConverter productDtoConverter() {
        return new ProductDtoConverter();
    }

    @Bean
    public UserDtoConverter userDtoConverter() {
        return new UserDtoConverter();
    }

}
