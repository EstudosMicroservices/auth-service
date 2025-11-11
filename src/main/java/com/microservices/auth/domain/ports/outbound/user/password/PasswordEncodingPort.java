package com.microservices.auth.domain.ports.outbound.user.password;

public interface PasswordEncodingPort {

    String encode(String rawPassword);
}
