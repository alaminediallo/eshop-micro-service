package com.lamine.isi.productservice.dto;

import com.lamine.isi.productservice.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    // Conversion de Product en ProductDTO
    public static ProductDTO toProductDTO(Product product) {
        if (product == null) {
            return null;
        }

/*        UserDTO ownerDTO = null;

        if (product.getOwner() != null) {
            ownerDTO = toUserDTO(product.getOwner());
        }*/

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
//        dto.setOwner(product.getOwner());
        dto.setOwnerId(product.getId());

        return dto;
    }

    // Conversion de Category en CategoryDTO
    public static CategoryDTO toCategoryDTO(Long categoryId) {
        if (categoryId == null) {
            return null;
        }

        return new CategoryDTO(categoryId, null);
    }

    // Conversion de Product en ProductWithCategoriesDTO
/*
    public static ProductWithCategoriesDTO toProductWithCategoriesDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductWithCategoriesDTO dto = new ProductWithCategoriesDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());

        if (product.getCategoryId() != null) {
            dto.setCategory(toCategoryDTO(product.getCategoryId()));
        }

  */
/*      if (product.getOwner() != null) {
            dto.setOwner(toUserDTO(product.get));
        }*//*


        return dto;
    }
*/

    // Conversion de ProductDTO en Product (pour la création/mise à jour)
    public static Product toProduct(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());

        // Vérifier si un owner est spécifié via l'objet owner
        if (dto.getOwner() != null && dto.getOwner().getId() != null) {
            UserDTO owner = new UserDTO();
            owner.setId(dto.getOwner().getId());
            product.setOwner(owner.getId());
        }
        // Si owner n'est pas spécifié mais ownerId est présent, utiliser cet ID
        else if (dto.getOwnerId() != null) {
            UserDTO owner = new UserDTO();
            owner.setId(dto.getOwnerId());
            product.setOwner(owner.getId());
        }

        return product;
    }

/*    // Conversion de CategoryDTO en Category (pour la création/mise à jour)
    public static CategoryDTO toCategory(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        CategoryDTO category = new CategoryDTO();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category;
    }*/

    // Conversion d'une liste de Product en liste de ProductDTO
    public static List<ProductDTO> toProductDTOList(List<Product> products) {
        if (products == null) {
            return List.of();
        }
        return products.stream()
                .map(ProductMapper::toProductDTO)
                .collect(Collectors.toList());
    } // Conversion d'une liste d'IDs de catégories en liste de CategoryDTO

/*
    public static List<CategoryDTO> toCategoryDTOList(List<Long> categoryIds) {
        if (categoryIds == null) {
            return List.of();
        }
        return categoryIds.stream()
                .map(ProductMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }
*/

    // Conversion de User en UserDTO
    public static UserDTO toUserDTO(UserDTO user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }
}