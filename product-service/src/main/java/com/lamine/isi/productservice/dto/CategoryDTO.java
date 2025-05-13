package com.lamine.isi.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;

    @NotBlank(message = "Le nom de la catégorie ne peut pas être vide")
    @Size(min = 2, max = 50, message = "Le nom de la catégorie doit contenir entre 2 et 50 caractères")
    private String name;
}