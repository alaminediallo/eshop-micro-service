package com.lamine.isi.categoryservice.controller;


import com.lamine.isi.categoryservice.dto.CategoryDTO;
import com.lamine.isi.categoryservice.dto.CategoryMapper;
import com.lamine.isi.categoryservice.model.Category;
import com.lamine.isi.categoryservice.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAll();
        return ResponseEntity.ok(CategoryMapper.toCategoryDTOList(categories));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toCategory(categoryDTO);
        Category savedCategory = categoryService.save(category);
        return new ResponseEntity<>(CategoryMapper.toCategoryDTO(savedCategory), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(CategoryMapper.toCategoryDTO(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toCategory(categoryDTO);
        Category updatedCategory = categoryService.update(id, category);
        return ResponseEntity.ok(CategoryMapper.toCategoryDTO(updatedCategory));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDTO> partialUpdateCategory(@PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        Category updatedCategory = categoryService.partialUpdate(id, updates);
        return ResponseEntity.ok(CategoryMapper.toCategoryDTO(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
