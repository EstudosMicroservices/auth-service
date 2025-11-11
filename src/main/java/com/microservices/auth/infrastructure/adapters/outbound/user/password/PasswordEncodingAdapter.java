package com.microservices.auth.infrastructure.adapters.outbound.user.password;

import com.microservices.auth.domain.ports.outbound.user.password.PasswordEncodingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncodingAdapter implements PasswordEncodingPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
