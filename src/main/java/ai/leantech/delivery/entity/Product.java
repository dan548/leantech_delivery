package ai.leantech.delivery.entity;

import lombok.*;

import javax.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table (name = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    private int price;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 228;
    }

}
