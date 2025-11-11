package com.microservices.auth.application.implementations.user.resetpass;

import com.microservices.auth.application.exceptions.custom.token.TokenAlreadyExistsException;
import com.microservices.auth.application.exceptions.custom.user.UserAlreadyAuthenticatedException;
import com.microservices.auth.domain.model.user.UserAuthDomain;
import com.microservices.auth.domain.ports.inbound.token.resetpass.UpdatePasswordTokenGeneratePort;
import com.microservices.auth.domain.ports.inbound.user.password.RequestResetPasswordPort;
import com.microservices.auth.domain.ports.outbound.token.resetpass.query.ResetPassTokenQueryPort;
import com.microservices.auth.domain.ports.outbound.token.resetpass.store.ResetPassTokenStorePort;
import com.microservices.auth.domain.ports.outbound.user.AuthUserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestResetPasswordImpl implements RequestResetPasswordPort {

    private final ResetPassTokenStorePort resetPassTokenStorePort;
    private final ResetPassTokenQueryPort resetPassTokenQueryPort;
    private final UpdatePasswordTokenGeneratePort updatePasswordTokenGeneratePort;
    private final AuthUserRepositoryPort authUserRepositoryPort;

    @Override
    public void requestPasswordReset(String email) {
        UserAuthDomain userAuthentication = authUserRepositoryPort.findByEmail(email).orElseThrow(
                () -> new UserAlreadyAuthenticatedException("Not found.")
        );

        String token = updatePasswordTokenGeneratePort.generateResetPasswordToken();
        if (resetPassTokenQueryPort.findResetTokenDetails(token).isPresent()) {
            throw new TokenAlreadyExistsException("This token exists!");
        }
        resetPassTokenStorePort.storeResetPasswordToken(userAuthentication.getId(), token, 30);
    }
}
