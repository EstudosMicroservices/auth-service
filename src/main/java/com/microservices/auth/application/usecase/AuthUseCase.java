package com.microservices.auth.application.usecase;

import com.microservices.auth.application.dto.AuthRequestDto;
import com.microservices.auth.application.dto.SessionDto;
import com.microservices.auth.application.dto.UserDto;

public interface AuthUseCase{

    String authenticate(AuthRequestDto authRequestDto);
    String logout(SessionDto sessionDto);
    String addToBlacklist(String token, long duration);
    boolean verifySession(String request);
    UserDto getUser(String id);
    boolean verifyTokenWithId(String id);
}
