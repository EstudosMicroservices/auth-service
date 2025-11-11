package com.microservices.auth.application.implementations.token.generate;

import com.microservices.auth.domain.ports.inbound.token.resetpass.UpdatePasswordTokenGeneratePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class UpdatePasswordTokenImpl implements UpdatePasswordTokenGeneratePort {

    private final Logger log = LoggerFactory.getLogger(UpdatePasswordTokenImpl.class);
    private final Random random;

    @Override
    public String generateResetPasswordToken() {
        log.info("[UpdatePasswordTokenImpl] Generating reset password token.");
        int randomToken = 100000 + random.nextInt(900000);
        return String.valueOf(randomToken);
    }
}
