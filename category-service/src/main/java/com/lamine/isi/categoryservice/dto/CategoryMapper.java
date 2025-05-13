package com.lamine.isi.categoryservice.dto;

import com.lamine.isi.categoryservice.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {
    // Conversion de Category en CategoryDTO
    public static CategoryDTO toCategoryDTO(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDTO(
                category.getId(),
                category.getName());
    }

    // Conversion de CategoryDTO en Category (pour la création/mise à jour)
    public static Category toCategory(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category;
    }

    // Conversion d'une liste de Category en liste de CategoryDTO
    public static List<CategoryDTO> toCategoryDTOList(List<Category> categories) {
        if (categories == null) {
            return List.of();
        }
        return new ArrayList<>(categories).stream()
                .map(CategoryMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }


}