package com.neonix.api.ecomerce.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")  // Nombre exacto de la tabla en mysql
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Para IDENTITY(1,1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String nombre;

    @Column(name = "description", length = 500)
    private String descripcion;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "count_in_stock", nullable = false)
    private Integer stock = 0;  // Default 0

    @ManyToOne(fetch = FetchType.LAZY)  // Relación con Category (opcional, carga lazy para rendimiento)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();  // Default GETDATE(), pero en Java usamos now()


    // Constructores
    public Product() {}

    public Product(String nombre, BigDecimal precio, String descripcion, Integer stock, Category category, String imageUrl) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.category = category;
        
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}