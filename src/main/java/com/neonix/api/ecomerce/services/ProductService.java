package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.Product;
import com.neonix.api.ecomerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setNombre(productDetails.getNombre());
        product.setDescripcion(productDetails.getDescripcion());
        product.setPrecio(productDetails.getPrecio());
        product.setStock(productDetails.getStock());
        product.setCategory(productDetails.getCategory());
        return repository.save(product);
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}