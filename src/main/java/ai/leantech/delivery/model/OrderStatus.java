package ai.leantech.delivery.model;

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
}
