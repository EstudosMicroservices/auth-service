package com.microservices.auth.application.usecase;

import com.auth0.jwt.algorithms.Algorithm;

import javax.crypto.SecretKey;

public interface TokenUseCase {

    String generateToken(String id);
    Algorithm encryptor(String jjwtSecret);
    SecretKey getSecretKey();
    boolean validateToken(String token);
    void invalidateToken(String token);
    void addToBlacklist(String token, long duration);
    boolean verifyIfTokenIsStored(String id);

}
