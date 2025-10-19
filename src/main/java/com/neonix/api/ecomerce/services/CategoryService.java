package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.Category;
import com.neonix.api.ecomerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category) {
        if (category.getId() == null) {
            category.setCreatedAt(LocalDateTime.now());
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Integer id, Category categoryDetails) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            Category category = existingCategory.get();
            category.setName(categoryDetails.getName());
            category.setDescription(categoryDetails.getDescription());
            // created_at no se actualiza
            return categoryRepository.save(category);
        }
        return null; // O lanzar una excepción
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    public boolean categoryExists(Integer id) {
        return categoryRepository.existsById(id);
    }
}