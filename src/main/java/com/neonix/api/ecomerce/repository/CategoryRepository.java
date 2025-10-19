package com.neonix.api.ecomerce.repository; // <--- ¡Ajusta tu paquete!

import com.neonix.api.ecomerce.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Este método es muy útil para el seeder para verificar si una categoría ya existe por su nombre.
    Optional<Category> findByName(String name);
}