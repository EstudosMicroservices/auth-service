package com.microservices.auth.application.implementations.token.secret;

import com.auth0.jwt.algorithms.Algorithm;
import com.microservices.auth.domain.ports.inbound.token.authentication.secret.AuthTokenEncryptorPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenEncryptorImpl implements AuthTokenEncryptorPort {

    @Value("${jjwtSecret}")
    private String jjwtSecret;

    @Override
    public Algorithm encryptor(String jjwtSecret) {
        // I don't think this needs any logging.
        return Algorithm.HMAC256(jjwtSecret);
    }
}
