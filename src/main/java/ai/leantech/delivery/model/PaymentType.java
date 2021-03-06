package ai.leantech.delivery.model;

import java.util.Arrays;

public enum PaymentType {
    CASH("cash"), CARD("card");

    private final String string;

    PaymentType(String s) {
        this.string = s;
    }

    @Override
    public String toString() {
        return string;
    }

    public static PaymentType findTypeByName(final String typeName) {
        return Arrays.stream(values())
                .filter(x -> x.toString().equals(typeName))
                .findAny()
                .orElse(null);
    }
}
