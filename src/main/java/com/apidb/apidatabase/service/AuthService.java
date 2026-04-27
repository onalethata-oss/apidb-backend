package com.apidb.apidatabase.service;

import com.apidb.apidatabase.config.JwtUtil;
import com.apidb.apidatabase.entity.User;
import com.apidb.apidatabase.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ LOGIN
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    // ✅ REGISTER
    public String register(String name, String email, String password, String role) {

        // Check if email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // ✅ Hash password
        user.setRole("USER"); // ✅ force clean role

        userRepository.save(user);

        // Return JWT token after registration
        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }
}