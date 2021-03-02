package ai.leantech.delivery.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String payment;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "delivery_guy_id")
    private User deliveryGuy;

    private String address;
    private String status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

}
