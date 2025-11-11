package com.microservices.auth.infrastructure.adapters.outbound.token.resetpass.query;

import com.microservices.auth.domain.model.ResetPassToken;
import com.microservices.auth.domain.ports.outbound.token.resetpass.query.ResetPassTokenQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ResetPassTokenQueryAdapter implements ResetPassTokenQueryPort {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String RESET_TOKEN_TO_USER_PREFIX = "reset:token:";
    private static final String USER_TO_RESET_TOKEN_PREFIX = "reset:user:";


    @Override
    public Optional<ResetPassToken> findResetTokenDetails(String token) {
        String tokenKey = RESET_TOKEN_TO_USER_PREFIX + token;
        String userId = stringRedisTemplate.opsForValue().get(tokenKey);

        if (userId == null) {
            return Optional.empty();
        }

        Long durationInSeconds = stringRedisTemplate.getExpire(tokenKey, TimeUnit.SECONDS);
        if (durationInSeconds == null || durationInSeconds <= 0) {
            return Optional.empty();
        }

        Instant expirationTime = Instant.now().plusSeconds(durationInSeconds);
        ResetPassToken resetToken = new ResetPassToken(userId, token, expirationTime);

        return Optional.of(resetToken);
    }

    @Override
    public Optional<String> findResetTokenByUserId(String userId) {
        String userKey = USER_TO_RESET_TOKEN_PREFIX + userId;
        return Optional.ofNullable(stringRedisTemplate.opsForValue().get(userKey));
    }
}
