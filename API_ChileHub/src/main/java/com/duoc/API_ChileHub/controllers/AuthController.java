package com.duoc.API_ChileHub.controllers;

import com.duoc.API_ChileHub.model.User;
import com.duoc.API_ChileHub.security.JwtUtil;
import com.duoc.API_ChileHub.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600) // ⬅️ Permitir todos los orígenes temporalmente
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 📝 Registro
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        System.out.println("\n===================================");
        System.out.println("📝 REGISTRO - Petición recibida");
        System.out.println("Email: " + user.getEmail());
        System.out.println("Name: " + user.getName());
        System.out.println("===================================\n");

        if (userService.findByEmail(user.getEmail()) != null) {
            System.out.println("❌ Email ya registrado");
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("user");
        User saved = userService.save(user);

        System.out.println("✅ Usuario registrado exitosamente con ID: " + saved.getId());

        return ResponseEntity.ok(saved);
    }

    // 🔐 Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        System.out.println("\n===================================");
        System.out.println("🔐 LOGIN - Petición recibida");

        String email = credentials.get("email");
        String password = credentials.get("password");

        System.out.println("Email: " + email);
        System.out.println("===================================\n");

        User user = userService.findByEmail(email);

        if (user == null) {
            System.out.println("❌ Usuario no encontrado");
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        System.out.println("🔍 Usuario encontrado, verificando contraseña...");

        boolean passwordMatch = passwordEncoder.matches(password, user.getPassword());
        System.out.println("¿Contraseña coincide? " + passwordMatch);

        if (!passwordMatch) {
            System.out.println("❌ Contraseña incorrecta");
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        String accessToken = jwtUtil.generateToken(email, user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(email);

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        response.put("user", user);

        System.out.println("✅ Login exitoso\n");

        return ResponseEntity.ok(response);
    }

    // 🔄 Renovar token
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        try {
            String email = jwtUtil.extractEmail(refreshToken);
            User user = userService.findByEmail(email);

            if (user != null && jwtUtil.validateToken(refreshToken, email)) {
                String newAccessToken = jwtUtil.generateToken(email, user.getRole());
                return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido");
        }

        return ResponseEntity.status(401).body("Refresh token expirado");
    }
}