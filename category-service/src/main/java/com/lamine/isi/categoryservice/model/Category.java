package com.lamine.isi.categoryservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la catégorie ne peut pas être vide")
    @Size(min = 2, max = 50, message = "Le nom de la catégorie doit contenir entre 2 et 50 caractères")
    @Column(unique = true)
    private String name;
}
