package ai.leantech.delivery.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="order_items")
public class OrderItem {

    @Id
    private long id;
    private int quantity;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="product_id", referencedColumnName="id")
    private Product product;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="order_id", referencedColumnName="id")
    private Order order;

}
