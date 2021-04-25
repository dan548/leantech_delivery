package ai.leantech.delivery.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return null;
        }
        return orderStatus.toString();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        return Stream.of(OrderStatus.values())
                .filter(c -> c.toString().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
