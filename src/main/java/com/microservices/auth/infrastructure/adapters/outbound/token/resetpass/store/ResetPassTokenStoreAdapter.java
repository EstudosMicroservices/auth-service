package com.microservices.auth.infrastructure.adapters.outbound.token.resetpass.store;

import com.microservices.auth.domain.ports.outbound.token.resetpass.store.ResetPassTokenStorePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ResetPassTokenStoreAdapter implements ResetPassTokenStorePort {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String RESET_TOKEN_TO_USER_PREFIX = "reset:token:";
    private static final String USER_TO_RESET_TOKEN_PREFIX = "reset:user:";

    @Override
    public void storeResetPasswordToken(String userId, String token, long durationMinutes) {
        String tokenKey = RESET_TOKEN_TO_USER_PREFIX + token;
        stringRedisTemplate.opsForValue().set(tokenKey, userId, durationMinutes, TimeUnit.MINUTES);

        String userKey = USER_TO_RESET_TOKEN_PREFIX + userId;
        stringRedisTemplate.opsForValue().set(userKey, token, durationMinutes, TimeUnit.MINUTES);
    }

    @Override
    public void deletePasswordResetToken(String token) {
        String tokenKey = RESET_TOKEN_TO_USER_PREFIX + token;
        String userId = stringRedisTemplate.opsForValue().get(tokenKey);

        if (userId != null) {
            String userKey = USER_TO_RESET_TOKEN_PREFIX + userId;
            stringRedisTemplate.delete(userKey);
        }
        stringRedisTemplate.delete(tokenKey);
    }
}
