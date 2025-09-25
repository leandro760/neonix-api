package com.neonix.api.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// Update the import below to match the actual location of Product.java
// For example, if Product.java is in 'com.neonix.api.ecommerce.model', correct the spelling:
import com.neonix.api.ecomerce.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}