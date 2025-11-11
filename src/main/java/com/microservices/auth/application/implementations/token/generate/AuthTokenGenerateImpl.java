package com.microservices.auth.application.implementations.token.generate;

import com.microservices.auth.domain.model.roles.UserRoles;
import com.microservices.auth.domain.ports.inbound.token.authentication.generate.AuthTokenGeneratePort;
import com.microservices.auth.domain.ports.outbound.token.authentication.secret.AuthTokenSecretPort;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthTokenGenerateImpl implements AuthTokenGeneratePort {

    private final Logger log = LoggerFactory.getLogger(AuthTokenGenerateImpl.class);
    private final AuthTokenSecretPort authTokenSecretPort;

    @Override
    public String generateToken(String userId, Set<UserRoles> roles) {
        log.info("[AuthTokenGenerateImpl] Generating token with id: {}, and roles: {}", userId, roles);
        List<String> roleNames = roles.stream()
                .map(UserRoles::name)
                .toList();
        return Jwts.builder()
                .subject(userId)
                .claim("roles", roleNames)
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
                .signWith(authTokenSecretPort.getSecretKey())
                .compact();
    }
}
