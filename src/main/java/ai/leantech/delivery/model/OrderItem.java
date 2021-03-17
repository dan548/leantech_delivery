package ai.leantech.delivery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name="order_items")
@EqualsAndHashCode(exclude="order")
public class OrderItem {

    @Id
    private long id;
    private int quantity;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="product_id", referencedColumnName="id")
    private Product product;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="order_id", referencedColumnName="id")
    @JsonBackReference
    private Order order;

}
