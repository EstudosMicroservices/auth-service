package com.microservices.auth.application.usecaseimpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.microservices.auth.application.usecase.TokenUseCase;
import com.microservices.auth.domain.ports.outbound.TokenRepositoryPort;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RequiredArgsConstructor
public class TokenUseCaseImpl implements TokenUseCase {

    private final String jjwtSecret;
    private final TokenRepositoryPort tokenRepositoryPort;

    @Override
    public String generateToken(String id) {
        return Jwts.builder()
                .subject(id)
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public Algorithm encryptor(String jjwtSecret) {
        return Algorithm.HMAC256(jjwtSecret);
    }

    @Override
    public SecretKey getSecretKey() {
        return new SecretKeySpec(jjwtSecret.getBytes(), "HmacSHA256");
    }

    @Override
    public boolean validateToken(String token) {
        if (tokenRepositoryPort.isTokenBlacklisted(token)) {
            return false;
        }
        Algorithm algorithm = encryptor(jjwtSecret);
        JWT.require(algorithm)
                .build()
                .verify(token)
                .getToken();
        return true;
    }

    @Override
    public void invalidateToken(String token) {
        tokenRepositoryPort.deleteToken(token);
    }

    @Override
    public void addToBlacklist(String token, long duration) {
        tokenRepositoryPort.addTokenToBlacklist(token, duration);
    }

    @Override
    public boolean verifyIfTokenIsStored(String id) {
        return tokenRepositoryPort.isTokenWithIdStored(id);
    }
}
