package com.neonix.api.ecomerce.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Importar para evitar recursión infinita en JSON

@Entity
@Table(name = "categories") // ¡Importante! Usar minúsculas y plural para el nombre de la tabla por convención SQL
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, length = 255) // Añadida longitud
    private String name;

    @Column(name = "description", columnDefinition = "TEXT") // Usar TEXT para descripciones largas
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Relación OneToMany con Product
    // @JsonIgnoreProperties("category") es crucial para evitar StackOverflowError al serializar JSON
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("category") // Ignora la categoría en el producto al serializar Category
    private Set<Product> products = new HashSet<>();


    // --- Constructores ---
    public Category() {
        // Constructor vacío necesario para JPA
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        // createdAt y updatedAt serán gestionados por los métodos @PrePersist
    }


    // --- Getters y Setters ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    // Opcional: toString() para depuración
    @Override
    public String toString() {
        return "Category{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }
}