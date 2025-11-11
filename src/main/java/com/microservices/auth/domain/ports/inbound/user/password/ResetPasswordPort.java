package com.microservices.auth.domain.ports.inbound.user.password;

public interface ResetPasswordPort {

    void resetPassword(String token, String newPassword);
}
