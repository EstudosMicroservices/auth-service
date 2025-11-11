package com.microservices.auth.domain.ports.inbound.user.password;

public interface RequestResetPasswordPort {

    void requestPasswordReset (String email);
}
