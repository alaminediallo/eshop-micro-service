package com.lamine.isi.productservice.service;

//import com.lamine.eshop.model.Category;

import com.lamine.isi.productservice.dto.CategoryDTO;
import com.lamine.isi.productservice.dto.ProductDTO;
import com.lamine.isi.productservice.dto.ProductWithCategoriesDTO;
import com.lamine.isi.productservice.dto.UserDTO;
import com.lamine.isi.productservice.model.Product;

import java.util.List;
import java.util.Map;

public interface IProductService {
    List<Product> getAll();

    Product getById(Long id);

    Product save(Product product);

    Product update(Long id, Product product);

    Product partialUpdate(Long id, Map<String, Object> updates);

    void delete(Long id);

    CategoryDTO getCategory(Long productId);

    void setCategory(Long productId, Long categoryId);

    void removeCategory(Long productId);

    UserDTO getOwner(Long productId);

    void setOwner(Long productId, Long userId);

    void removeOwner(Long productId);

    List<Product> getProductsByOwnerId(Long ownerId);

    ProductWithCategoriesDTO getProductWithFullDetails(Long id);

    List<ProductDTO> getAllWithUserDetails();

    List<ProductDTO> toProductDTOList(List<Product> products);
}
