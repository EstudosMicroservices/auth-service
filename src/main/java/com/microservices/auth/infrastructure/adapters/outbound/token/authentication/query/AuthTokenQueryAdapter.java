package com.microservices.auth.infrastructure.adapters.outbound.token.authentication.query;

import com.microservices.auth.domain.ports.outbound.token.authentication.hash.AuthTokenHashPort;
import com.microservices.auth.domain.ports.outbound.token.authentication.query.AuthTokenQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthTokenQueryAdapter implements AuthTokenQueryPort {

    private final StringRedisTemplate stringRedisTemplate;
    private final AuthTokenHashPort authTokenHashPort;

    private static final String ACTIVE_USER_TOKEN_PREFIX = "active_user_token:"; // userId -> token
    private static final String ACTIVE_TOKEN_USER_PREFIX = "active_token_user:"; // hash(token) -> userId (ou JSON de claims)
    private static final String BLACKLISTED_TOKEN_PREFIX = "blacklisted_token:"; // hash(token) -> "blacklisted"
    private static final String BLACKLISTED_VALUE = "blacklisted";

    @Override
    public String getTokenById(String userId) {
        return stringRedisTemplate.opsForValue().get(ACTIVE_USER_TOKEN_PREFIX + userId);
    }

    @Override
    public boolean isTokenWithIdStored(String userId) {

        // Embora a IDE mostre um warning, este método é mais seguro que a forma simplificada
        // stringRedisTemplate.hasKey(ACTIVE_USER_TOKEN_PREFIX + userId)
        // porque caso retorne null, ele retorna um false, evitando NullPointerException

        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(ACTIVE_USER_TOKEN_PREFIX + userId));

    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        String tokenHash = authTokenHashPort.hashToken(token);
        String value = stringRedisTemplate.opsForValue().get(BLACKLISTED_TOKEN_PREFIX + tokenHash);
        return BLACKLISTED_VALUE.equals(value);
    }

    @Override
    public boolean isActiveToken(String token) {
        String tokenHash = authTokenHashPort.hashToken(token);

        // Embora a IDE mostre um warning, este método é mais seguro que a forma simplificada
        // stringRedisTemplate.hasKey(ACTIVE_USER_TOKEN_PREFIX + userId)
        // porque caso retorne null, ele retorna um false, evitando NullPointerException

        return !Boolean.TRUE.equals(stringRedisTemplate.hasKey(ACTIVE_TOKEN_USER_PREFIX + tokenHash));
    }

    @Override
    public Set<String> getAllActiveUserIds() {
        return Set.of();
    }
}
