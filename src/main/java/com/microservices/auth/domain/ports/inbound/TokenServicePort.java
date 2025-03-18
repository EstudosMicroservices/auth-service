package com.microservices.auth.domain.ports.inbound;

import com.auth0.jwt.algorithms.Algorithm;

import javax.crypto.SecretKey;

public interface TokenServicePort {

    String generateToken(String id);

    Algorithm encryptor(String jjwtSecret);

    SecretKey getSecretKey();

    boolean validateToken(String token);

    void invalidateToken(String token);

    void addToBlacklist(String token, long duration);

    boolean verifyIfTokenIsStored(String id);
}
