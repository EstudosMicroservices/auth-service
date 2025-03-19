package com.microservices.auth.application.services;

import com.auth0.jwt.algorithms.Algorithm;
import com.microservices.auth.application.usecaseimpl.TokenUseCaseImpl;
import com.microservices.auth.domain.ports.inbound.TokenServicePort;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;

@RequiredArgsConstructor
public class TokenServiceImpl implements TokenServicePort {

    private final TokenUseCaseImpl tokenUseCaseImpl;

    @Override
    public String generateToken(String id) {
        return tokenUseCaseImpl.generateToken(id);
    }

    @Override
    public Algorithm encryptor(String jjwtSecret) {
        return tokenUseCaseImpl.encryptor(jjwtSecret);
    }

    @Override
    public SecretKey getSecretKey() {
        return tokenUseCaseImpl.getSecretKey();
    }

    @Override
    public boolean validateToken(String token) {
        return tokenUseCaseImpl.validateToken(token);
    }

    @Override
    public void invalidateToken(String token) {
        tokenUseCaseImpl.invalidateToken(token);
    }

    @Override
    public void addToBlacklist(String token, long duration) {
        tokenUseCaseImpl.addToBlacklist(token, duration);
    }


    @Override
    public boolean verifyIfTokenIsStored(String id) {
//      Add custom exception later.
        if(!tokenUseCaseImpl.verifyIfTokenIsStored(id)){
            throw new RuntimeException();
        }
        return true;
    }
}
