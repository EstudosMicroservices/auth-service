package com.microservices.auth.domain.ports.outbound.token.resetpass.query;

import com.microservices.auth.domain.model.ResetPassToken;

import java.util.Optional;

public interface ResetPassTokenQueryPort {

    Optional<ResetPassToken> findResetTokenDetails(String token);

    Optional<String> findResetTokenByUserId(String userId);
}
