package com.codersquare.registration.token;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public void saveConfirmationToken(Token token) {
        tokenRepository.save(token);
    }

    public Token getToken(String token) {
        return tokenRepository.findTokenByConfirmationToken(token)
                .orElseThrow(() -> new  EntityNotFoundException("token not found"));
    }

    public void setConfiramedAt(String token){
        tokenRepository.updateTokenByConfirmedAt(token, LocalDateTime.now());
    }



}
