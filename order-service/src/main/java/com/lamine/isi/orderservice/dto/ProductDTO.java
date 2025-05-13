package com.lamine.isi.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private String name;

    @Min(value = 100, message = "Le prix doit être supérieur à 100")
    private double price;

    private int quantity;

    private UserDTO owner;

    private Long ownerId;
}