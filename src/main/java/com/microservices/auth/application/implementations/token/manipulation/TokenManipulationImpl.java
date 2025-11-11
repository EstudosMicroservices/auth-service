package com.microservices.auth.application.implementations.token.manipulation;

import com.microservices.auth.domain.ports.inbound.token.authentication.secret.AuthTokenEncryptorPort;
import com.microservices.auth.domain.ports.inbound.token.manipulation.TokenManipulationPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TokenManipulationImpl implements TokenManipulationPort {

    @Value("${jjwtSecret}")
    private String jjwtSecret;
    private final AuthTokenEncryptorPort authTokenEncryptorPort;
    private final Logger logger = LoggerFactory.getLogger(TokenManipulationImpl.class);

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private SecretKey getSignInKey() {
        byte[] keyBytes = jjwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }
}