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

    public DatabaseSeeder(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        seedCategories();
        seedProducts();
    }

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
        System.out.println("Iniciando siembra de categorías...");
        seedCategory("Buzos", "Prendas con diseño urbano y tejidos de alta calidad.");
        seedCategory("Camisetas", "Camisetas urbanas con estilo moderno y cómodo.");
        seedCategory("Pantalones", "Pantalones casual y urbanos con estilo confort.");
        seedCategory("Accesorios", "Complementos para tu look urbano.");
        seedCategory("Destacados", "Productos destacados por su popularidad y estilo.");
        System.out.println("Siembra de categorías completada.");
    }

    private void seedProducts() {
        System.out.println("Iniciando siembra de productos...");

        // Eliminar todos los productos existentes
        productRepository.deleteAll();
        System.out.println("Todos los productos existentes han sido eliminados.");

        // Recuperar categorías
        Category buzosCategory = categoryRepository.findByName("Buzos")
            .orElseGet(() -> seedCategory("Buzos", "Prendas con diseño urbano y tejidos de alta calidad."));
        Category camisetasCategory = categoryRepository.findByName("Camisetas")
            .orElseGet(() -> seedCategory("Camisetas", "Camisetas urbanas con estilo moderno y cómodo."));
        Category pantalonesCategory = categoryRepository.findByName("Pantalones")
            .orElseGet(() -> seedCategory("Pantalones", "Pantalones casual y urbanos con estilo confort."));
        Category accesoriosCategory = categoryRepository.findByName("Accesorios")
            .orElseGet(() -> seedCategory("Accesorios", "Complementos para tu look urbano."));
        Category destacadosCategory = categoryRepository.findByName("Destacados")
            .orElseGet(() -> seedCategory("Destacados", "Productos destacados por su popularidad y estilo."));

        // Agregar nuevos productos

        // Destacados
        Product p1 = new Product();
        p1.setName("Set Midnight Drop");
        p1.setDescription("Set Midnight Drop – producto destacado.");
        p1.setPrice(new BigDecimal("180000.00"));
        p1.setStock(100); // ejemplo de stock
        p1.setCategory(destacadosCategory);
        p1.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Set_Midnight_Drop_quaube");
        productRepository.save(p1);
        System.out.println("Producto '" + p1.getName() + "' sembrado.");

        Product p2 = new Product();
        p2.setName("Camiseta Tag Master");
        p2.setDescription("Camiseta Tag Master – producto destacado.");
        p2.setPrice(new BigDecimal("97000.00"));
        p2.setStock(100);
        p2.setCategory(destacadosCategory);
        p2.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Camiseta_Tag_Master_nr3ofk");
        productRepository.save(p2);
        System.out.println("Producto '" + p2.getName() + "' sembrado.");

        Product p3 = new Product();
        p3.setName("Zapatillas Pulse Sneaker");
        p3.setDescription("Zapatillas Pulse Sneaker – producto destacado.");
        p3.setPrice(new BigDecimal("320000.00"));
        p3.setStock(100);
        p3.setCategory(destacadosCategory);
        p3.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Zapatillas_Pulse_Sneaker_ke0owe");
        productRepository.save(p3);
        System.out.println("Producto '" + p3.getName() + "' sembrado.");

        Product p4 = new Product();
        p4.setName("Buzo Neon Vibe");
        p4.setDescription("Buzo Neon Vibe – producto destacado.");
        p4.setPrice(new BigDecimal("120000.00"));
        p4.setStock(100);
        p4.setCategory(destacadosCategory);
        p4.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Buzo_Neon_Vibe_u7bpj6");
        productRepository.save(p4);
        System.out.println("Producto '" + p4.getName() + "' sembrado.");

        Product p5 = new Product();
        p5.setName("Combo Essential Pack");
        p5.setDescription("Combo Essential Pack – producto destacado.");
        p5.setPrice(new BigDecimal("280000.00"));
        p5.setStock(100);
        p5.setCategory(destacadosCategory);
        p5.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Combo_Essential_Pack_ralpe8");
        productRepository.save(p5);
        System.out.println("Producto '" + p5.getName() + "' sembrado.");

        // Buzos
        Product p6 = new Product();
        p6.setName("Buzo Street Vibe");
        p6.setDescription("Buzo Street Vibe.");
        p6.setPrice(new BigDecimal("120000.00"));
        p6.setStock(100);
        p6.setCategory(buzosCategory);
        p6.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Buzo_Street_Vibe_cvzies");
        productRepository.save(p6);
        System.out.println("Producto '" + p6.getName() + "' sembrado.");

        Product p7 = new Product();
        p7.setName("Buzo Urban Pulse");
        p7.setDescription("Buzo Urban Pulse.");
        p7.setPrice(new BigDecimal("100000.00"));
        p7.setStock(100);
        p7.setCategory(buzosCategory);
        p7.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Buzo_Urban_Pulse_ey9zm6");
        productRepository.save(p7);
        System.out.println("Producto '" + p7.getName() + "' sembrado.");

        Product p8 = new Product();
        p8.setName("Buzo Metro Drift");
        p8.setDescription("Buzo Metro Drift.");
        p8.setPrice(new BigDecimal("120000.00"));
        p8.setStock(100);
        p8.setCategory(buzosCategory);
        p8.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Buzo_Metro_Drift_bwxrde");
        productRepository.save(p8);
        System.out.println("Producto '" + p8.getName() + "' sembrado.");

        Product p9 = new Product();
        p9.setName("Buzo Noise Layer");
        p9.setDescription("Buzo Noise Layer.");
        p9.setPrice(new BigDecimal("120000.00"));
        p9.setStock(100);
        p9.setCategory(buzosCategory);
        p9.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Buzo_Noise_Layer_tzrqdg");
        productRepository.save(p9);
        System.out.println("Producto '" + p9.getName() + "' sembrado.");

        Product p10 = new Product();
        p10.setName("Buzo Night Shift");
        p10.setDescription("Buzo Night Shift.");
        p10.setPrice(new BigDecimal("120000.00"));
        p10.setStock(100);
        p10.setCategory(buzosCategory);
        p10.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Buzo_Night_Shift_lrow8q");
        productRepository.save(p10);
        System.out.println("Producto '" + p10.getName() + "' sembrado.");

        // Camisetas
        Product p11 = new Product();
        p11.setName("Camiseta Core Street");
        p11.setDescription("Camiseta Core Street.");
        p11.setPrice(new BigDecimal("70000.00"));
        p11.setStock(100);
        p11.setCategory(camisetasCategory);
        p11.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Camiseta_Core_Street_fxauom");
        productRepository.save(p11);
        System.out.println("Producto '" + p11.getName() + "' sembrado.");

        Product p12 = new Product();
        p12.setName("Camiseta Tag Life");
        p12.setDescription("Camiseta Tag Life.");
        p12.setPrice(new BigDecimal("70000.00"));
        p12.setStock(100);
        p12.setCategory(camisetasCategory);
        p12.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Camiseta_Tag_Life_gjgxnu");
        productRepository.save(p12);
        System.out.println("Producto '" + p12.getName() + "' sembrado.");

        Product p13 = new Product();
        p13.setName("Camiseta Concrete Flow");
        p13.setDescription("Camiseta Concrete Flow.");
        p13.setPrice(new BigDecimal("70000.00"));
        p13.setStock(100);
        p13.setCategory(camisetasCategory);
        p13.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Camiseta_Concrete_Flow_zbhzoa");
        productRepository.save(p13);
        System.out.println("Producto '" + p13.getName() + "' sembrado.");

        Product p14 = new Product();
        p14.setName("Camiseta Skyline Edge");
        p14.setDescription("Camiseta Skyline Edge.");
        p14.setPrice(new BigDecimal("70000.00"));
        p14.setStock(100);
        p14.setCategory(camisetasCategory);
        p14.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Camiseta_Skyline_Edge_nv68b5");
        productRepository.save(p14);
        System.out.println("Producto '" + p14.getName() + "' sembrado.");

        Product p15 = new Product();
        p15.setName("Camiseta Raw District");
        p15.setDescription("Camiseta Raw District.");
        p15.setPrice(new BigDecimal("70000.00"));
        p15.setStock(100);
        p15.setCategory(camisetasCategory);
        p15.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Camiseta_Raw_District_octcxw");
        productRepository.save(p15);
        System.out.println("Producto '" + p15.getName() + "' sembrado.");

        // Pantalones
        Product p16 = new Product();
        p16.setName("Jogger Grind Mode");
        p16.setDescription("Jogger Grind Mode.");
        p16.setPrice(new BigDecimal("90000.00"));
        p16.setStock(100);
        p16.setCategory(pantalonesCategory);
        p16.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Jogger_Grind_Mode_awzdg0");
        productRepository.save(p16);
        System.out.println("Producto '" + p16.getName() + "' sembrado.");

        Product p17 = new Product();
        p17.setName("Cargo District Flex");
        p17.setDescription("Cargo District Flex.");
        p17.setPrice(new BigDecimal("110000.00"));
        p17.setStock(100);
        p17.setCategory(pantalonesCategory);
        p17.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Cargo_District_Flex_roplx8");
        productRepository.save(p17);
        System.out.println("Producto '" + p17.getName() + "' sembrado.");

        Product p18 = new Product();
        p18.setName("Jean Underground");
        p18.setDescription("Jean Underground.");
        p18.setPrice(new BigDecimal("130000.00"));
        p18.setStock(100);
        p18.setCategory(pantalonesCategory);
        p18.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Jean_Underground_tuipk6");
        productRepository.save(p18);
        System.out.println("Producto '" + p18.getName() + "' sembrado.");

        Product p19 = new Product();
        p19.setName("Pantalón Metroline");
        p19.setDescription("Pantalón Metroline.");
        p19.setPrice(new BigDecimal("100000.00"));
        p19.setStock(100);
        p19.setCategory(pantalonesCategory);
        p19.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Pantal%C3%B3n_Metroline_myex1m");
        productRepository.save(p19);
        System.out.println("Producto '" + p19.getName() + "' sembrado.");

        Product p20 = new Product();
        p20.setName("Pantalón Backalley Utility");
        p20.setDescription("Pantalón Backalley Utility.");
        p20.setPrice(new BigDecimal("120000.00"));
        p20.setStock(100);
        p20.setCategory(pantalonesCategory);
        p20.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Pantal%C3%B3n_Backalley_Utility_iprvu9");
        productRepository.save(p20);
        System.out.println("Producto '" + p20.getName() + "' sembrado.");

        // Accesorios
        Product p21 = new Product();
        p21.setName("Gorra Concrete");
        p21.setDescription("Gorra Concrete.");
        p21.setPrice(new BigDecimal("80000.00"));
        p21.setStock(100);
        p21.setCategory(accesoriosCategory);
        p21.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Gorra_Concrete_lkid5q");
        productRepository.save(p21);
        System.out.println("Producto '" + p21.getName() + "' sembrado.");

        Product p22 = new Product();
        p22.setName("Gorro Loop");
        p22.setDescription("Gorro Loop.");
        p22.setPrice(new BigDecimal("70000.00"));
        p22.setStock(100);
        p22.setCategory(accesoriosCategory);
        p22.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Gorro_Loop_snwxuq");
        productRepository.save(p22);
        System.out.println("Producto '" + p22.getName() + "' sembrado.");

        Product p23 = new Product();
        p23.setName("Lentes Block Shades");
        p23.setDescription("Lentes Block Shades.");
        p23.setPrice(new BigDecimal("150000.00"));
        p23.setStock(100);
        p23.setCategory(accesoriosCategory);
        p23.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Lentes_Block_Shades_i4dli8");
        productRepository.save(p23);
        System.out.println("Producto '" + p23.getName() + "' sembrado.");

        Product p24 = new Product();
        p24.setName("Medias Urban Stride (3 pares)");
        p24.setDescription("Medias Urban Stride (3 pares).");
        p24.setPrice(new BigDecimal("60000.00"));
        p24.setStock(100);
        p24.setCategory(accesoriosCategory);
        p24.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Medias_Urban_Stride_fodi7a");
        productRepository.save(p24);
        System.out.println("Producto '" + p24.getName() + "' sembrado.");

        Product p25 = new Product();
        p25.setName("Riñonera Core Side Bag");
        p25.setDescription("Riñonera Core Side Bag.");
        p25.setPrice(new BigDecimal("200000.00"));
        p25.setStock(100);
        p25.setCategory(accesoriosCategory);
        p25.setImageUrl("https://res.cloudinary.com/ddzetix8t/image/upload/Ri%C3%B1onera_Core_Side_Bag_um0hly");
        productRepository.save(p25);
        System.out.println("Producto '" + p25.getName() + "' sembrado.");

        System.out.println("Siembra de productos completada.");
    }
}
