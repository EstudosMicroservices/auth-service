package com.microservices.auth.infrastructure.adapters.outbound.token.authentication.secret;

import com.microservices.auth.domain.ports.outbound.token.authentication.secret.AuthTokenSecretPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class AuthTokenSecretAdapter implements AuthTokenSecretPort {

    private final String jjwtSecret;

    public AuthTokenSecretAdapter(@Value("${jjwtSecret}") String jjwtSecret) {
        this.jjwtSecret = jjwtSecret;
    }

    @Override
    public SecretKey getSecretKey() {
        return new SecretKeySpec(jjwtSecret.getBytes(), "HmacSHA256");

    }
}
