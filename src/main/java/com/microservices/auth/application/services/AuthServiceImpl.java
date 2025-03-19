package com.microservices.auth.application.services;

import com.microservices.auth.application.dto.AuthRequestDto;
import com.microservices.auth.application.dto.SessionDto;
import com.microservices.auth.application.usecaseimpl.AuthUseCaseImpl;
import com.microservices.auth.domain.ports.inbound.AuthServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthServicePort {

    private final AuthUseCaseImpl authUseCaseImpl;

    @Override
    public String authenticate(AuthRequestDto authRequestDto) {
        return authUseCaseImpl.authenticate(authRequestDto);
    }

    @Override
    public String logout(SessionDto sessionDto) {
        return authUseCaseImpl.logout(sessionDto);
    }

    @Override
    public void verifySession(String request) {
        authUseCaseImpl.verifySession(request);
    }

    @Override
    public void verifyTokenWithId(String id) {
        authUseCaseImpl.verifyTokenWithId(id);
    }


}
