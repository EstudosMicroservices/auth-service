package com.microservices.auth.domain.ports.outbound.token.authentication.store;

public interface AuthTokenStorePort {

    void storeToken(String userId, String token, long durationMinutes);

    void revokeToken(String token);

    void addTokenToBlacklist(String token, long durationMinutes);
}
