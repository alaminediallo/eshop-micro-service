package com.lamine.isi.categoryservice.service;

import com.lamine.isi.categoryservice.model.Category;

import java.util.List;
import java.util.Map;

public interface ICategoryService {
    List<Category> getAll();

    Category getById(Long id);

    Category save(Category category);

    Category update(Long id, Category category);

    Category partialUpdate(Long id, Map<String, Object> updates);

    void delete(Long id);
}
