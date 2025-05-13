package com.lamine.isi.productservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private String name;

    @Min(value = 100, message = "Le prix doit être supérieur à 100")
    private double price;

    @ColumnDefault("0")
    private int quantity;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "user_id")
    private Long owner;
}
