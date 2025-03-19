package com.microservices.auth.infrastructure.adapters.outbound;

import com.microservices.auth.domain.ports.outbound.TokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class TokenAdapter implements TokenRepositoryPort {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void storeToken(String userId, String token, long duration) {
        stringRedisTemplate.opsForValue().set(userId, token, duration * 3600, TimeUnit.SECONDS
        );
    }

    @Override
    public String getTokenById(String id) {
        return stringRedisTemplate.opsForValue().get(id);
    }

    @Override
    public boolean isTokenWithIdStored(String id) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(id));
    }


    @Override
    public void deleteToken(String token) {
        stringRedisTemplate.delete(token);
    }

    @Override
    public void addTokenToBlacklist(String token, long duration) {
        stringRedisTemplate.opsForValue().set(token, "blacklisted", duration * 3600, TimeUnit.SECONDS);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(token));
    }


}
