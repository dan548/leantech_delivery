package ai.leantech.delivery.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

import javax.persistence.*;

@Data
@Entity
@Table (name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;
    private int price;

}
