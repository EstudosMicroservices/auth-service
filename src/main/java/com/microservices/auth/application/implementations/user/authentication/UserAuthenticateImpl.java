package com.microservices.auth.application.implementations.user.authentication;

import com.microservices.auth.application.exceptions.custom.user.UserNotAuthenticatedException;
import com.microservices.auth.domain.model.user.UserAuthDomain;
import com.microservices.auth.domain.ports.inbound.token.authentication.generate.AuthTokenGeneratePort;
import com.microservices.auth.domain.ports.inbound.user.authentication.UserAuthenticatePort;
import com.microservices.auth.domain.ports.outbound.token.authentication.store.AuthTokenStorePort;
import com.microservices.auth.domain.ports.outbound.user.AuthUserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthenticateImpl implements UserAuthenticatePort {

    private static final Logger log = LoggerFactory.getLogger(UserAuthenticateImpl.class);
    private final AuthenticationManager authenticationManager;
    private final AuthUserRepositoryPort authUserRepositoryPort;
    private final AuthTokenGeneratePort authTokenGeneratePort;
    private final AuthTokenStorePort authTokenStorePort;

    @Override
    public String authenticate(UserAuthDomain userAuthDomain) {
        log.info("[UserAuthenticateImpl] Starting authentication service.");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthDomain.getEmail(), userAuthDomain.getSenha())
        );

        // Just using 'instanceof' as Pattern Matching, I'm not necessarily casting expressions
        // Usando 'instanceof' como Pattern Matching, não estou necessariamente fazendo casting de expressões.
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            UserAuthDomain authenticatedUser = authUserRepositoryPort.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> {
                        log.error("[UserAuthenticateImpl] Failed to find user by email: {}", userDetails.getUsername());
                        return new UserNotAuthenticatedException("User not found in authentication service database after successful authentication. This indicates a data inconsistency.");
                    });

            String token = authTokenGeneratePort.generateToken(userDetails.getUsername(), authenticatedUser.getRoles());
            authTokenStorePort.storeToken(userDetails.getUsername(), token, 24 * 3600);
            log.info("[UserAuthenticateImpl] User with email {}, successfully authenticated!", userDetails.getUsername());
            return token;
        } else {
            log.error("[UserAuthenticateImpl] Type mismatch in authentication process!");
            throw new IllegalStateException("Authenticated principal is not of type UserDetails.");
        }
    }
}

