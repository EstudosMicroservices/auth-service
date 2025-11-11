package com.microservices.auth.domain.ports.outbound.token.authentication.hash;

public interface AuthTokenHashPort {

    String hashToken(String token);
}
