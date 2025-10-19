package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.Category;
import com.neonix.api.ecomerce.models.Product;
import com.neonix.api.ecomerce.repository.CategoryRepository;
import com.neonix.api.ecomerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Integer id) {
        return productRepository.findById(id);
    }

    public List<Product> findProductsByCategoryId(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product saveProduct(Product product) {
        if (product.getId() == null) {
            product.setCreatedAt(LocalDateTime.now());
        }
        product.setUpdatedAt(LocalDateTime.now());

        // Manejar la categoría
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Optional<Category> category = categoryRepository.findById(product.getCategory().getId());
            category.ifPresent(product::setCategory);
        } else {
            product.setCategory(null); // Asegurarse de que si no se proporciona, se establezca a null
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Integer id, Product productDetails) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStock(productDetails.getStock());
            product.setImageUrl(productDetails.getImageUrl());
            product.setUpdatedAt(LocalDateTime.now());

            // Manejar la categoría en la actualización
            if (productDetails.getCategory() != null && productDetails.getCategory().getId() != null) {
                Optional<Category> category = categoryRepository.findById(productDetails.getCategory().getId());
                category.ifPresent(product::setCategory);
            } else {
                product.setCategory(null); // Permite desvincular la categoría si se pasa null o categoría sin ID
            }

            return productRepository.save(product);
        }
        return null; // O lanzar una excepción
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public boolean productExists(Integer id) {
        return productRepository.existsById(id);
    }
}