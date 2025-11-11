package com.microservices.auth.application.implementations.user.resetpass;

import com.microservices.auth.application.exceptions.custom.token.TokenDoesNotExistsException;
import com.microservices.auth.application.exceptions.custom.user.UserNotAuthenticatedException;
import com.microservices.auth.domain.model.ResetPassToken;
import com.microservices.auth.domain.model.user.UserAuthDomain;
import com.microservices.auth.domain.ports.inbound.user.password.ResetPasswordPort;
import com.microservices.auth.domain.ports.outbound.token.resetpass.query.ResetPassTokenQueryPort;
import com.microservices.auth.domain.ports.outbound.user.AuthUserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResetPasswordImpl implements ResetPasswordPort {

    private final AuthUserRepositoryPort authUserRepositoryPort;
    private final ResetPassTokenQueryPort resetPassTokenQueryPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void resetPassword(String token, String newPassword) {
        Optional<ResetPassToken> resetPassToken = resetPassTokenQueryPort.findResetTokenDetails(token);
        if (resetPassToken.isEmpty()) {
            throw new TokenDoesNotExistsException("Invalid token");
        }
        UserAuthDomain userAuthentication = authUserRepositoryPort.findById(resetPassToken.get().getIdUser())
                .orElseThrow(() -> new UserNotAuthenticatedException("User not found!"));
        String encodedPassword = passwordEncoder.encode(newPassword);
        userAuthentication.setSenha(encodedPassword);
        authUserRepositoryPort.save(userAuthentication);

    }
}
