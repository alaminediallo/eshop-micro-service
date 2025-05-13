package com.lamine.isi.categoryservice.service;

import com.lamine.isi.categoryservice.exception.NotFoundException;
import com.lamine.isi.categoryservice.model.Category;
import com.lamine.isi.categoryservice.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Catégorie non trouvée avec l'ID: " + id));
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Catégorie non trouvée avec l'ID: " + id);
        }
        category.setId(id);
        return categoryRepository.save(category);
    }

    @Override
    public Category partialUpdate(Long id, Map<String, Object> updates) {
        Category category = getById(id);

        if (updates.containsKey("name")) {
            category.setName((String) updates.get("name"));
        }

        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Catégorie non trouvée avec l'ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
