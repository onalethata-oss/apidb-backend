package com.apidb.apidatabase.controller;

import com.apidb.apidatabase.service.AuthService;
import com.apidb.apidatabase.dto.LoginRequest;
import com.apidb.apidatabase.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(
            loginRequest.getEmail(),
            loginRequest.getPassword()
        );
        return ResponseEntity.ok(token);
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        String token = authService.register(
            registerRequest.getName(),
            registerRequest.getEmail(),
            registerRequest.getPassword(),
            registerRequest.getRole()
        );
        return ResponseEntity.ok(token);
    }
}