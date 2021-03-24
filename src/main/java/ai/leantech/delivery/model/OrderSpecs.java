package ai.leantech.delivery.model;

import ai.leantech.delivery.entity.Order;
import ai.leantech.delivery.entity.Order_;
import ai.leantech.delivery.entity.User_;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class OrderSpecs {

    public static Specification<Order> hasCustomerWithId(Long customerId) {
        return (root, query, builder) -> {
            if (customerId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.join(Order_.client).get(User_.id), customerId);
        };
    }

    public static Specification<Order> hasCourierWithId(Long courierId) {
        return (root, query, builder) -> {
            if (courierId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.join(Order_.courier).get(User_.id), courierId);
        };
    }

    public static Specification<Order> hasNullCourier() {
        return (root, query, builder) -> builder.isNull(root.get(Order_.courier));
    }

    public static Specification<Order> isStatus(OrderStatus status) {
        return (root, query, builder) -> {
            if (status == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get(Order_.status), status);
        };
    }

    public static Specification<Order> isPaymentType(PaymentType type) {
        return (root, query, builder) -> {
            if (type == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get(Order_.paymentType), type);
        };
    }
}
