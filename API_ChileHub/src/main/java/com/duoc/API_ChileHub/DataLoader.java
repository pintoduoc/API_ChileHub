package com.duoc.API_ChileHub;

import com.duoc.API_ChileHub.model.Product;
import com.duoc.API_ChileHub.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Product(0, "Suscripción Mensual", 29.99, "Acceso completo durante 1 mes.", "/images/sub_mensual.png"));
                repository.save(new Product(0, "Suscripción Semanal", 9.99, "Acceso durante 1 semana.", "/images/sub_semanal.png"));
                repository.save(new Product(0, "Suscripción Anual", 249.99, "Acceso durante 1 año.", "/images/sub_anual.jpeg"));
                System.out.println("✅ Productos cargados en la base de datos H2");
            }
        };
    }
}