package com.microservices.auth.application.implementations.token.verify;

import com.microservices.auth.application.exceptions.custom.token.TokenDoesNotExistsException;
import com.microservices.auth.domain.ports.inbound.token.authentication.verify.AuthTokenVerifyPort;
import com.microservices.auth.domain.ports.inbound.token.manipulation.TokenManipulationPort;
import com.microservices.auth.domain.ports.outbound.token.authentication.query.AuthTokenQueryPort;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenVerifyImpl implements AuthTokenVerifyPort {

    private static final Logger log = LoggerFactory.getLogger(AuthTokenVerifyImpl.class);
    private final AuthTokenQueryPort authTokenQueryPort;
    private final TokenManipulationPort tokenManipulationPort;

    @Override
    public void verifyToken(String userId) {
        log.info("[AuthTokenVerifyImpl.verifyToken] Starting token verification.");

        if (!authTokenQueryPort.isTokenWithIdStored(userId)) {
            log.error("[AuthTokenVerifyImpl.verifyToken] Token with id {} isn't stored", userId);
            throw new TokenDoesNotExistsException("No token with this userId stored!");
        }

    }

    @Override
    public void verifySession(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SessionException("Session isn't valid - no authentication found");
        }

        String token = authentication.getCredentials().toString();
        String username = authentication.getName();

        log.info("[AuthTokenVerifyImpl.verifySession] Verifying session for user: {}", username);

        if (authTokenQueryPort.isTokenBlacklisted(token)) {
            log.error("[AuthTokenVerifyImpl.verifySession] Token {} is blacklisted.", token);
            throw new SessionException("Session isn't valid - token revoked");
        }

        if (!tokenManipulationPort.extractUsername(token).equals(username)) {
            log.error("[AuthTokenVerifyImpl.verifySession] Token username mismatch. Token: {}, Authenticated: {}"
                    , tokenManipulationPort.extractUsername(token), username);
            throw new SessionException("Session isn't valid - username mismatch");
        }

        if (tokenManipulationPort.isTokenExpired(token)) {
            log.error("[AuthTokenVerifyImpl.verifySession] Token expired for user: {}", username);
            throw new SessionException("Session isn't valid - token expired");
        }

        log.info("[AuthTokenVerifyImpl.verifySession] Session successfully verified for user: {}", username);
    }

}
