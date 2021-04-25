package ai.leantech.delivery.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PaymentTypeConverter implements AttributeConverter<PaymentType, String> {

    @Override
    public String convertToDatabaseColumn(PaymentType paymentType) {
        if (paymentType == null) {
            return null;
        }
        return paymentType.toString();
    }

    @Override
    public PaymentType convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        return Stream.of(PaymentType.values())
                .filter(c -> c.toString().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}