package com.microservices.auth.domain.ports.outbound.token.resetpass.store;

public interface ResetPassTokenStorePort {

    void storeResetPasswordToken(String userId, String token, long durationMinutes);

    void deletePasswordResetToken(String token);


}
