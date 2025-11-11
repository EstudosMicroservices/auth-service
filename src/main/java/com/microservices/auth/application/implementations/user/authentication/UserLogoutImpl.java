package com.microservices.auth.application.implementations.user.authentication;

import com.microservices.auth.domain.ports.inbound.user.authentication.UserLogoutPort;
import com.microservices.auth.domain.ports.outbound.token.authentication.query.AuthTokenQueryPort;
import com.microservices.auth.domain.ports.outbound.token.authentication.store.AuthTokenStorePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLogoutImpl implements UserLogoutPort {

    private static final Logger log = LoggerFactory.getLogger(UserLogoutImpl.class);
    private final AuthTokenStorePort authTokenStorePort;
    private final AuthTokenQueryPort authTokenQueryPort;

    @Override
    public void logout(String token) {
        log.info("[UserLogoutImpl] Starting logout process!");

        boolean alreadyBlacklisted = authTokenQueryPort.isTokenBlacklisted(token);
        boolean isActive = authTokenQueryPort.isTokenWithIdStored(token);

        if (alreadyBlacklisted || !isActive) {
            log.warn("[UserLogoutImpl] Token is already invalid or blacklisted.");
            return;
        }

        authTokenStorePort.revokeToken(token);
        log.info("[UserLogoutImpl] Token successfully revoked.");
    }

}
