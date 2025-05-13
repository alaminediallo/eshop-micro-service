package com.lamine.isi.productservice.controller;

import com.lamine.isi.productservice.client.IUserClient;
import com.lamine.isi.productservice.dto.*;
import com.lamine.isi.productservice.model.Product;
import com.lamine.isi.productservice.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;
    private final IUserClient userClient;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
//        List<Product> products = productService.getAll();
        List<ProductDTO> productDTOs = productService.getAllWithUserDetails();
        return ResponseEntity.ok(productDTOs);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        // Conversion du DTO en entité
        Product product = ProductMapper.toProduct(productDTO);

        // Assigner le propriétaire
        setOwnerFromDTO(product, productDTO);

        // Sauvegarde du produit avec son propriétaire
        Product savedProduct = productService.save(product);

        // Retourne le produit créé avec toutes ses informations
        return new ResponseEntity<>(ProductMapper.toProductDTO(savedProduct), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductWithCategoriesDTO> getProductById(@PathVariable Long id) {
//        Product product = productService.getById(id);
        return ResponseEntity.ok(productService.getProductWithFullDetails(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        Product product = ProductMapper.toProduct(productDTO);

        // Assigner le propriétaire
        setOwnerFromDTO(product, productDTO);

        Product updatedProduct = productService.update(id, product);
        return ResponseEntity.ok(ProductMapper.toProductDTO(updatedProduct));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> partialUpdateProduct(@PathVariable Long id,
                                                           @RequestBody Map<String, Object> updates) {
        Product updatedProduct = productService.partialUpdate(id, updates);
        return ResponseEntity.ok(ProductMapper.toProductDTO(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/category")
    public ResponseEntity<CategoryDTO> getProductCategory(@PathVariable Long id) {
        CategoryDTO category = productService.getCategory(id);
        if (category == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ProductMapper.toCategoryDTO(category.getId()));
    }

    @PostMapping("/{id}/category/{categoryId}")
    public ResponseEntity<Void> setCategoryToProduct(@PathVariable Long id, @PathVariable Long categoryId) {
        productService.setCategory(id, categoryId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/category/{categoryId}")
    public ResponseEntity<Void> updateProductCategory(@PathVariable Long id, @PathVariable Long categoryId) {
        productService.setCategory(id, categoryId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/category")
    public ResponseEntity<Void> removeCategoryFromProduct(@PathVariable Long id) {
        productService.removeCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/owner")
    public ResponseEntity<UserDTO> getProductOwner(@PathVariable Long id) {
        UserDTO owner = productService.getOwner(id);
        if (owner == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ProductMapper.toUserDTO(owner));
    }

    @PostMapping("/{id}/owner/{userId}")
    public ResponseEntity<Void> setOwnerToProduct(@PathVariable Long id, @PathVariable Long userId) {
        productService.setOwner(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/owner/{userId}")
    public ResponseEntity<Void> updateProductOwner(@PathVariable Long id, @PathVariable Long userId) {
        productService.setOwner(id, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/owner")
    public ResponseEntity<Void> removeOwnerFromProduct(@PathVariable Long id) {
        productService.removeOwner(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductDTO>> getProductsByOwner(@PathVariable Long userId) {
        List<Product> products = productService.getProductsByOwnerId(userId);
        List<ProductDTO> productDTOs = productService.toProductDTOList(products);
        return ResponseEntity.ok(productDTOs);
    }

    private void setOwnerFromDTO(Product product, ProductDTO productDTO) {
        // Si un propriétaire est spécifié dans le DTO, récupérer l'utilisateur complet
        if (product.getOwner() != null) {
            UserDTO user = userClient.getUserById(product.getOwner());
            product.setOwner(user.getId());
        }
        // Vérifier aussi le cas où ownerId est fourni directement
        else if (productDTO.getOwnerId() != null) {
            UserDTO user = userClient.getUserById(productDTO.getOwnerId());
            product.setOwner(user.getId());
        }
    }
}
