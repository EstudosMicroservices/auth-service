package com.microservices.auth.domain.ports.outbound;

public interface TokenRepositoryPort {

    void storeToken(String userId, String token, long duration);

    String getTokenById(String id);

    boolean isTokenWithIdStored(String id);

    void deleteToken(String token);

    void addTokenToBlacklist(String token, long duration);

    boolean isTokenBlacklisted(String token);
}
