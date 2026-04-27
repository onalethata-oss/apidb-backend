package com.apidb.apidatabase.controller;

import com.apidb.apidatabase.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    // ✅ Generate a token for a specific user
    @PostMapping("/generate/{userId}")
    public ResponseEntity<String> generateToken(@PathVariable Long userId) {

        String token = tokenService.generateToken(userId);

        return ResponseEntity.ok(token);
    }
}