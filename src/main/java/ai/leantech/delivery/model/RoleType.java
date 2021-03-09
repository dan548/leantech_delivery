package ai.leantech.delivery.model;

public enum RoleType {
    ADMIN("ADMIN"), COURIER("DELIVERY"), CUSTOMER("CLIENT");

    private final String string;

    RoleType(String s) {
        this.string = s;
    }

    @Override
    public String toString() {
        return string;
    }
}
