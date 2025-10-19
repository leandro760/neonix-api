package com.neonix.api.ecomerce;

import com.neonix.api.ecomerce.models.Category;
import com.neonix.api.ecomerce.models.Product;
import com.neonix.api.ecomerce.repository.CategoryRepository;
import com.neonix.api.ecomerce.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    // Constructor para inyectar los repositorios
    public DatabaseSeeder(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional // Asegura que todo se ejecute en una sola transacción
    public void run(String... args) throws Exception {
        seedCategories(); // Llama primero a la siembra de categorías
        seedProducts(); // Luego, siembra los productos
    }

    /**
     * Método para sembrar categorías.
     * Verifica si la categoría ya existe por su nombre antes de crearla.
     */
    private Category seedCategory(String name, String description) {
        Optional<Category> existingCategory = categoryRepository.findByName(name);
        if (existingCategory.isPresent()) {
            System.out.println("Categoría '" + name + "' ya existe.");
            return existingCategory.get();
        } else {
            Category newCategory = new Category(name, description);
            categoryRepository.save(newCategory);
            System.out.println("Categoría '" + name + "' sembrada.");
            return newCategory;
        }
    }

    private void seedCategories() {
        // Sembrar tus categorías aquí
        System.out.println("Iniciando siembra de categorías...");
        Category buzosCategory = seedCategory("Buzos", "Prendas de abrigo para la parte superior del cuerpo.");
        Category remerasCategory = seedCategory("Remeras", "Prendas ligeras para la parte superior del cuerpo."); // Corregido: consistente
        // Añade más categorías si lo deseas
        System.out.println("Siembra de categorías completada.");
    }

    /**
     * Método para sembrar productos.
     * Este método asume que las categorías ya han sido sembradas.
     */
    private void seedProducts() {
        if (productRepository.count() == 0) { // Solo siembra productos si la tabla está vacía
            System.out.println("Iniciando siembra de productos...");

            // Recupera las categorías que necesitas para los productos
            // Usamos orElseGet para evitar excepción si no existe (por seguridad)
            Category buzosCategory = categoryRepository.findByName("Buzos")
                    .orElseGet(() -> {
                        System.err.println("Advertencia: Categoría 'Buzos' no encontrada, creando una nueva.");
                        return seedCategory("Buzos", "Prendas de abrigo para la parte superior del cuerpo.");
                    });
            Category remerasCategory = categoryRepository.findByName("Remeras")
                    .orElseGet(() -> {
                        System.err.println("Advertencia: Categoría 'Remeras' no encontrada, creando una nueva.");
                        return seedCategory("Remeras", "Prendas ligeras para la parte superior del cuerpo.");
                    });

            // Crea tu primer producto con los datos que tenías
            Product product1 = new Product();
            product1.setName("Buzo Urban Pulse");
            product1.setDescription("Un buzo moderno y cómodo, perfecto para el estilo urbano.");
            product1.setPrice(new BigDecimal("100000.00"));
            product1.setStock(50);
            product1.setCategory(buzosCategory); // ¡Asigna el objeto Category!
            product1.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Buzo_Urban_Pulse_ey9zm6");

            productRepository.save(product1);
            System.out.println("Producto 'Buzo Urban Pulse' sembrado.");

            // Puedes añadir más productos aquí
            Product product2 = new Product();
            product2.setName("Remera Estampada Cool");
            product2.setDescription("Remera de algodón con estampado exclusivo.");
            product2.setPrice(new BigDecimal("35000.00"));
            product2.setStock(120);
            product2.setCategory(remerasCategory);
            product2.setImageUrl("https://ejemplo.com/remera_estampada_cool.jpg");
            productRepository.save(product2);
            System.out.println("Producto 'Remera Estampada Cool' sembrado.");

            System.out.println("Siembra de productos completada.");
        } else {
            System.out.println("La tabla de productos no está vacía, omitiendo siembra de productos.");
        }
    }
}