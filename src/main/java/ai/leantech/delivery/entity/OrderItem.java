package ai.leantech.delivery.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="order_items")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="product_id", referencedColumnName="id")
    private Product product;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="order_id", referencedColumnName="id")
    @JsonBackReference
    private Order order;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderItem other = (OrderItem) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 13;
    }

}
