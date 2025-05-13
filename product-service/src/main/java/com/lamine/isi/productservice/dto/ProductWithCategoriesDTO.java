package com.lamine.isi.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithCategoriesDTO {
    private Long id;
    private String name;
    private double price;
    private int quantity;
    private CategoryDTO category;
    private UserDTO owner;
}