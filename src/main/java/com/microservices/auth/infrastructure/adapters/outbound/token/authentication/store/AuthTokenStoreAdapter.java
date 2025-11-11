package com.microservices.auth.infrastructure.adapters.outbound.token.authentication.store;

import com.microservices.auth.domain.ports.outbound.token.authentication.hash.AuthTokenHashPort;
import com.microservices.auth.domain.ports.outbound.token.authentication.store.AuthTokenStorePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
public class AuthTokenStoreAdapter implements AuthTokenStorePort {

    private final StringRedisTemplate stringRedisTemplate;
    private final AuthTokenHashPort authTokenHashPort;

    private static final String ACTIVE_USER_TOKEN_PREFIX = "active_user_token:"; // userId -> token
    private static final String ACTIVE_TOKEN_USER_PREFIX = "active_token_user:"; // hash(token) -> userId (ou JSON de claims)
    private static final String BLACKLISTED_TOKEN_PREFIX = "blacklisted_token:"; // hash(token) -> "blacklisted"
    private static final String BLACKLISTED_VALUE = "blacklisted";

    @Override
    public void storeToken(String userId, String token, long durationMinutes) {
        String tokenHash = authTokenHashPort.hashToken(token);
        // Armazena userId -> token
        stringRedisTemplate.opsForValue().set(ACTIVE_USER_TOKEN_PREFIX + userId, token, durationMinutes, TimeUnit.MINUTES);

        // Armazena hash(token) -> userId
        // Mais eficiente
        stringRedisTemplate.opsForValue().set(ACTIVE_TOKEN_USER_PREFIX + tokenHash, userId, durationMinutes, TimeUnit.MINUTES);

    }

    @Override
    public void revokeToken(String token) {
        String tokenHash = authTokenHashPort.hashToken(token);
        String userId = stringRedisTemplate.opsForValue().get(ACTIVE_TOKEN_USER_PREFIX + tokenHash);

        if (userId != null) {
            // Remove a relação userId → token
            stringRedisTemplate.delete(ACTIVE_USER_TOKEN_PREFIX + userId);
            // Remove a relação token → userId
            stringRedisTemplate.delete(ACTIVE_TOKEN_USER_PREFIX + tokenHash);
        }

        // Adiciona o token na blacklist por 1 hora
        addTokenToBlacklist(token, 3600L);
    }



    @Override
    public void addTokenToBlacklist(String token, long durationMinutes) {
        String tokenHash = authTokenHashPort.hashToken(token);
        stringRedisTemplate.opsForValue().set(BLACKLISTED_TOKEN_PREFIX + tokenHash, BLACKLISTED_VALUE, durationMinutes, TimeUnit.SECONDS);

    }

}
