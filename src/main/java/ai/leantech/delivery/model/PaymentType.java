package ai.leantech.delivery.model;

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
}
