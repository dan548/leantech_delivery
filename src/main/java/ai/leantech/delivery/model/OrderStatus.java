package ai.leantech.delivery.model;

import java.util.Arrays;

public enum OrderStatus {
    CREATED("created"), ON_DELIVERY("delivery"), DONE("done");

    private final String string;

    OrderStatus(String s) {
        this.string = s;
    }

    @Override
    public String toString() {
        return string;
    }

    public static boolean isStatus(final String status) {
        return Arrays.stream(values())
                .anyMatch(x -> x.toString().equals(status));
    }

    public static OrderStatus findStatusByName(final String statusName) {
        return Arrays.stream(values())
                .filter(x -> x.toString().equals(statusName))
                .findAny()
                .orElse(null);
    }
}
