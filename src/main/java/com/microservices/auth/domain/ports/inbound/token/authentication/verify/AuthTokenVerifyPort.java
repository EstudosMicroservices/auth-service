package com.microservices.auth.domain.ports.inbound.token.authentication.verify;

import org.springframework.security.core.Authentication;

public interface AuthTokenVerifyPort {

    void verifyToken(String userId);

    void verifySession(Authentication authentication);

}
