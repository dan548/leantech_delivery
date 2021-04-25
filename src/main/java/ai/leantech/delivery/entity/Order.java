package ai.leantech.delivery.entity;

import ai.leantech.delivery.model.OrderStatus;
import ai.leantech.delivery.model.PaymentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_type")
    private PaymentType paymentType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "courier_id")
    private User courier;

    private String address;
    private OrderStatus status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User client;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private OffsetDateTime createdAt;
    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 666;
    }

}
