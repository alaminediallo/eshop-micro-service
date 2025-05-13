package com.lamine.isi.productservice.service;


import com.lamine.isi.productservice.client.ICategoryClient;
import com.lamine.isi.productservice.client.IUserClient;
import com.lamine.isi.productservice.dto.*;
import com.lamine.isi.productservice.exception.NotFoundException;
import com.lamine.isi.productservice.model.Product;
import com.lamine.isi.productservice.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ICategoryClient categoryClient;
    private final IUserClient userClient;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("product non trouvé avec l'ID: " + id));
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("product non trouvé avec l'ID: " + id);
        }
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public Product partialUpdate(Long id, Map<String, Object> updates) {
        Product product = getById(id);

        if (updates.containsKey("name")) {
            product.setName((String) updates.get("name"));
        }
        if (updates.containsKey("price")) {
            product.setPrice(((Number) updates.get("price")).doubleValue());
        }
        if (updates.containsKey("quantity")) {
            product.setQuantity(((Number) updates.get("quantity")).intValue());
        }

        if (updates.containsKey("ownerId")) {
            Long ownerId = ((Number) updates.get("ownerId")).longValue();
            UserDTO owner = userClient.getUserById(ownerId);
            if (owner == null) {
                throw new NotFoundException("Utilisateur non trouvé avec l'ID: " + ownerId);
            }
            product.setOwner(owner.getId());
        }

        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("product non trouvé avec l'ID: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public CategoryDTO getCategory(Long productId) {
        Product product = getById(productId);
        return categoryClient.getCategoryById(product.getOwner());
    }

    @Override
    public void setCategory(Long productId, Long categoryId) {
        Product product = getById(productId);

        CategoryDTO category = categoryClient.getCategoryById(categoryId);
        if (category == null) {
            throw new NotFoundException("Catégorie non trouvée avec l'ID: " + categoryId);

        }
        product.setCategoryId(categoryId);
        productRepository.save(product);
    }

    @Override
    public void removeCategory(Long productId) {
        Product product = getById(productId);
        product.setCategoryId(null);
        productRepository.save(product);
    }

    @Override
    public UserDTO getOwner(Long productId) {
        Product product = getById(productId);
        return userClient.getUserById(product.getOwner());
    }

    @Override
    public void setOwner(Long productId, Long userId) {
        Product product = getById(productId);

        UserDTO user = userClient.getUserById(userId);

        if (user == null) {
            throw new NotFoundException("Utilisateur non trouvée avec l'ID: " + userId);
        }
        product.setOwner(user.getId());
        productRepository.save(product);
    }

    @Override
    public void removeOwner(Long productId) {
        Product product = getById(productId);
        product.setOwner(null);
        productRepository.save(product);
    }

    @Override
    public List<Product> getProductsByOwnerId(Long ownerId) {
        if (userClient.getUserById(ownerId) == null) {
            throw new NotFoundException("Utilisateur non trouvé avec l'ID: " + ownerId);
        }

        return productRepository.findAll().stream()
                .filter(p -> p.getOwner() != null && p.getOwner().equals(ownerId))
                .collect(Collectors.toList());
    }

    public ProductWithCategoriesDTO getProductWithFullDetails(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductWithCategoriesDTO dto = new ProductWithCategoriesDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());

        // Appel à category-service
        if (product.getCategoryId() != null) {
            CategoryDTO category = categoryClient.getCategoryById(product.getCategoryId());
            dto.setCategory(category);
        }

        // Appel à user-service
        if (product.getOwner() != null) {
            UserDTO owner = userClient.getUserById(product.getOwner());
            dto.setOwner(owner);
        }

        return dto;
    }

    public List<ProductDTO> getAllWithUserDetails() {
        List<Product> products = productRepository.findAll();

        return getProductDTOS(products);
    }

    public List<ProductDTO> toProductDTOList(List<Product> products) {
        return getProductDTOS(products);
    }

    private List<ProductDTO> getProductDTOS(List<Product> products) {
        return products.stream().map(product -> {
            ProductDTO dto = ProductMapper.toProductDTO(product);

            if (product.getOwner() != null) {
                try {
                    UserDTO user = userClient.getUserById(product.getOwner());
                    dto.setOwner(user);
                } catch (Exception e) {
                    dto.setOwner(null);
                }
            }

            return dto;
        }).collect(Collectors.toList());
    }

}