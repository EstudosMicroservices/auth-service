package com.microservices.auth.domain.ports.inbound;

import com.microservices.auth.application.dto.AuthRequestDto;
import com.microservices.auth.application.dto.SessionDto;

public interface AuthServicePort {

    String authenticate(AuthRequestDto authRequestDto);

    String logout(SessionDto sessionDto);

    void verifySession(String request);

    void verifyTokenWithId(String id);
}
