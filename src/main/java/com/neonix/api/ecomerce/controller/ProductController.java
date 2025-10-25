package com.neonix.api.ecomerce.controller; 

import com.neonix.api.ecomerce.models.Product;
import com.neonix.api.ecomerce.models.Category;
import com.neonix.api.ecomerce.repository.ProductRepository;
import com.neonix.api.ecomerce.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // Inyección por constructor: mejor práctica
    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

     @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Optional<Category> categoryOpt = categoryRepository.findById(product.getCategory().getId());
            if (categoryOpt.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                    "Categoría con ID " + product.getCategory().getId() + " no encontrada.");
            }
            product.setCategory(categoryOpt.get());
        }
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @Valid @RequestBody Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(productDetails.getName());
            existingProduct.setDescription(productDetails.getDescription());
            existingProduct.setPrice(productDetails.getPrice());
            existingProduct.setStock(productDetails.getStock());
            existingProduct.setImageUrl(productDetails.getImageUrl());
            existingProduct.setUpdatedAt(LocalDateTime.now());

            // Manejo de categoría: resuelve por ID o establece null
            if (productDetails.getCategory() != null && productDetails.getCategory().getId() != null) {
                Optional<Category> categoryOpt = categoryRepository.findById(productDetails.getCategory().getId());
                if (categoryOpt.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                        "Categoría con ID " + productDetails.getCategory().getId() + " no encontrada.");
                }
                existingProduct.setCategory(categoryOpt.get());
            } else if (productDetails.getCategory() == null) {
                existingProduct.setCategory(null); // Permite desasignar categoría
            }

            Product updatedProduct = productRepository.save(existingProduct);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategoryId(@PathVariable Integer categoryId) {
        // Verificación temprana: si categoría no existe, error 404
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada.");
        }
        return productRepository.findByCategoryId(categoryId);
    }
}