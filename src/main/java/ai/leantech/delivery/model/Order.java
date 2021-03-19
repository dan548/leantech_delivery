package ai.leantech.delivery.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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


}
