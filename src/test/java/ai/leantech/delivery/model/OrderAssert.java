package ai.leantech.delivery.model;

import org.assertj.core.api.AbstractAssert;

public class OrderAssert extends AbstractAssert<OrderAssert, Order> {

    public OrderAssert(Order order) {
        super(order, OrderAssert.class);
    }

    public static OrderAssert assertThat(Order actual) {
        return new OrderAssert(actual);
    }

    public OrderAssert hasCreationDate() {
        isNotNull();
        if (actual.getCreatedAt() == null) {
            failWithMessage(
                    "Expected order to have a creation date, but it was null"
            );
        }
        return this;
    }
}
