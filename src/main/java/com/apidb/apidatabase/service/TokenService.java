package com.apidb.apidatabase.service;

import com.apidb.apidatabase.entity.User;
import com.apidb.apidatabase.entity.UserToken;
import com.apidb.apidatabase.repository.UserRepository;
import com.apidb.apidatabase.repository.UserTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private UserTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Generate a token for a user
    public String generateToken(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();

        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUser(user);
        userToken.setExpiresAt(LocalDateTime.now().plusDays(30));

        tokenRepository.save(userToken);

        return token;
    }

    // ✅ Check if a token is valid (FIXED)
    public boolean isValid(String token) {

        Optional<UserToken> tokenOpt = tokenRepository.findByToken(token);

        if (tokenOpt.isEmpty()) {
            return false;
        }

        UserToken userToken = tokenOpt.get();

        return userToken.isActive()
                && (userToken.getExpiresAt() == null
                || userToken.getExpiresAt().isAfter(LocalDateTime.now()));
    }
}