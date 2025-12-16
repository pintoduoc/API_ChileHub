package com.duoc.API_ChileHub.services;

import com.duoc.API_ChileHub.model.User;
import com.duoc.API_ChileHub.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {
        System.out.println("🔍 Buscando usuario con email: '" + email + "'"); // ⬅️ LOG

        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            System.out.println("✅ Usuario encontrado: " + user.getEmail()); // ⬅️ LOG
        } else {
            System.out.println("❌ Usuario NO encontrado"); // ⬅️ LOG

            // Mostrar todos los usuarios en BD
            System.out.println("📋 Usuarios en la base de datos:");
            userRepository.findAll().forEach(u ->
                    System.out.println("  - ID: " + u.getId() + ", Email: '" + u.getEmail() + "'")
            );
        }

        return user;
    }

    public User save(User user) {
        System.out.println("💾 Guardando usuario: " + user.getEmail()); // ⬅️ LOG
        User saved = userRepository.save(user);
        System.out.println("✅ Usuario guardado con ID: " + saved.getId()); // ⬅️ LOG
        return saved;
    }
}