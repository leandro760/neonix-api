package com.neonix.api.ecomerce;

import com.neonix.api.ecomerce.models.Category;
import com.neonix.api.ecomerce.models.Product;
import com.neonix.api.ecomerce.repository.CategoryRepository;
import com.neonix.api.ecomerce.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DatabaseSeeder(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seedCategories();
        seedProducts();
    }

    private Category seedCategory(String name, String description) {
        Optional<Category> existingCategory = categoryRepository.findByName(name);
        if (existingCategory.isPresent()) {
            return existingCategory.get();
        } else {
            Category newCategory = new Category(name, description);
            categoryRepository.save(newCategory);
            return newCategory;
        }
    }

    private void seedCategories() {
        seedCategory("Buzos", "Prendas con diseño urbano y tejidos de alta calidad.");
        seedCategory("Camisetas", "Camisetas urbanas con estilo moderno y cómodo.");
        seedCategory("Pantalones", "Pantalones casual y urbanos con estilo confort.");
        seedCategory("Accesorios", "Complementos para tu look urbano.");
        seedCategory("Destacados", "Productos destacados por su popularidad y estilo.");
    }

    private void seedProducts() {
        // Eliminar todos los productos existentes
        productRepository.deleteAll();

        // Recuperar categorías
        Category buzosCategory = categoryRepository.findByName("Buzos").orElseThrow();
        Category camisetasCategory = categoryRepository.findByName("Camisetas").orElseThrow();
        Category pantalonesCategory = categoryRepository.findByName("Pantalones").orElseThrow();
        Category accesoriosCategory = categoryRepository.findByName("Accesorios").orElseThrow();
        Category destacadosCategory = categoryRepository.findByName("Destacados").orElseThrow();

        // Lista de productos
        List<Product> products = Arrays.asList(
            // Destacados
            createProduct("Set Midnight Drop", "Set Midnight Drop – producto destacado.", "180000.00", 100, destacadosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Set_Midnight_Drop_quaube.png"),
            createProduct("Camiseta Tag Master", "Camiseta Tag Master – producto destacado.", "97000.00", 100, destacadosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Camiseta_Tag_Master_nr3ofk.png"),
            createProduct("Zapatillas Pulse Sneaker", "Zapatillas Pulse Sneaker – producto destacado.", "320000.00", 100, destacadosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Zapatillas_Pulse_Sneaker_ke0owe.png"),
            createProduct("Buzo Neon Vibe", "Buzo Neon Vibe – producto destacado.", "120000.00", 100, destacadosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Buzo_Neon_Vibe_u7bpj6.png"),
            createProduct("Combo Essential Pack", "Combo Essential Pack – producto destacado.", "280000.00", 100, destacadosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Combo_Essential_Pack_ralpe8.png"),

            // Buzos
            createProduct("Buzo Street Vibe", "Buzo Street Vibe.", "120000.00", 100, buzosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Buzo_Street_Vibe_cvzies.png"),
            createProduct("Buzo Urban Pulse", "Buzo Urban Pulse.", "100000.00", 100, buzosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Buzo_Urban_Pulse_ey9zm6.png"),
            createProduct("Buzo Metro Drift", "Buzo Metro Drift.", "120000.00", 100, buzosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Buzo_Metro_Drift_bwxrde.png"),
            createProduct("Buzo Noise Layer", "Buzo Noise Layer.", "120000.00", 100, buzosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Buzo_Noise_Layer_tzrqdg.png"),
            createProduct("Buzo Night Shift", "Buzo Night Shift.", "120000.00", 100, buzosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Buzo_Night_Shift_lrow8q.png"),

            // Camisetas
            createProduct("Camiseta Core Street", "Camiseta Core Street.", "70000.00", 100, camisetasCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Camiseta_Core_Street_fxauom.png"),
            createProduct("Camiseta Tag Life", "Camiseta Tag Life.", "70000.00", 100, camisetasCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Camiseta_Tag_Life_gjgxnu.png"),
            createProduct("Camiseta Concrete Flow", "Camiseta Concrete Flow.", "70000.00", 100, camisetasCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Camiseta_Concrete_Flow_zbhzoa.png"),
            createProduct("Camiseta Skyline Edge", "Camiseta Skyline Edge.", "70000.00", 100, camisetasCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Camiseta_Skyline_Edge_nv68b5.png"),
            createProduct("Camiseta Raw District", "Camiseta Raw District.", "70000.00", 100, camisetasCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Camiseta_Raw_District_octcxw.png"),

            // Pantalones
            createProduct("Jogger Grind Mode", "Jogger Grind Mode.", "90000.00", 100, pantalonesCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Jogger_Grind_Mode_awzdg0.png"),
            createProduct("Cargo District Flex", "Cargo District Flex.", "110000.00", 100, pantalonesCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Cargo_District_Flex_roplx8.png"),
            createProduct("Jean Underground", "Jean Underground.", "130000.00", 100, pantalonesCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Jean_Underground_tuipk6.png"),
            createProduct("Pantalón Metroline", "Pantalón Metroline.", "95000.00", 100, pantalonesCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Pantal%C3%B3n_Metroline_myex1m.png"),
            createProduct("Pantalón Backalley Utility", "Pantalón Backalley Utility.", "120000.00", 100, pantalonesCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Pantal%C3%B3n_Backalley_Utility_iprvu9.png"),

            // Accesorios
            createProduct("Gorra Concrete", "Gorra Concrete.", "50000.00", 100, accesoriosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Gorra_Concrete_lkid5q.png"),
            createProduct("Mochila Urban Pack", "Mochila Urban Pack.", "120000.00", 100, accesoriosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Mochila_Urban_Pack_fjlsbf.png"),
            createProduct("Gorra Night Vibe", "Gorra Night Vibe.", "50000.00", 100, accesoriosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Set_Midnight_Drop_quaube.png"),
            createProduct("Mochila Metroline", "Mochila Metroline.", "120000.00", 100, accesoriosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Mochila_Metroline_fmrugx.png"),
            createProduct("Cinturón Urban Flex", "Cinturón Urban Flex.", "40000.00", 100, accesoriosCategory, "https://res.cloudinary.com/ddzetix8t/image/upload/Cinturon_Urban_Flex_pflru0.png")
        );

        // Guardar todos los productos en la base de datos
        productRepository.saveAll(products);
    }

    private Product createProduct(String name, String description, String price, int stock, Category category, String imageUrl) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(new BigDecimal(price));
        product.setStock(stock);
        product.setCategory(category);
        product.setImageUrl(imageUrl);
        return product;
    }
}
