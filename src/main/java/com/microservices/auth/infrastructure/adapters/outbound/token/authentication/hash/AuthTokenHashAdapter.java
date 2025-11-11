package com.microservices.auth.infrastructure.adapters.outbound.token.authentication.hash;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.microservices.auth.domain.ports.outbound.token.authentication.hash.AuthTokenHashPort;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class AuthTokenHashAdapter implements AuthTokenHashPort {

    @Override
    public String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new AlgorithmMismatchException("SHA-256 algorithm not found", e);
        }
    }
}
