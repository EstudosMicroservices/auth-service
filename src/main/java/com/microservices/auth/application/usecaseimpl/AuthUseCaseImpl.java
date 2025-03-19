package com.microservices.auth.application.usecaseimpl;

import com.microservices.auth.application.dto.AuthRequestDto;
import com.microservices.auth.application.dto.SessionDto;
import com.microservices.auth.application.dto.UserDto;
import com.microservices.auth.application.services.TokenServiceImpl;
import com.microservices.auth.application.usecase.AuthUseCase;
import com.microservices.auth.domain.ports.outbound.TokenRepositoryPort;
import com.microservices.auth.infrastructure.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {

    private final TokenServiceImpl tokenServiceImpl;
    private final TokenRepositoryPort tokenRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final UserClient userClient;

    @Override
    public String authenticate(AuthRequestDto authRequestDto) {
//      Add custom exception later.
        UserDto user = getUser(authRequestDto.id());
        if(user == null || user.id() == null){
            throw new RuntimeException("User or Id is null");
        }
        if(!passwordEncoder.matches(authRequestDto.senha(), user.senha())){
            throw new RuntimeException("Invalid password matches.");
        }

        String token = tokenServiceImpl.generateToken(authRequestDto.id());
        tokenRepositoryPort.storeToken(authRequestDto.id(), token, 24);
        return token;
    }

    @Override
    public String logout(SessionDto sessionDto) {
        tokenServiceImpl.invalidateToken(sessionDto.token());
        return "Successful logout!";
    }

    @Override
    public String addToBlacklist(String token, long duration) {
        tokenServiceImpl.addToBlacklist(token, duration);
        return "Successfully added to black list!";
    }


    @Override
    public boolean verifySession(String request) {
//      Add custom exception later.
        boolean isValid = tokenServiceImpl.validateToken(request);
        if (!isValid){
            throw new RuntimeException("Token isn't valid!");
        }
        return true;
    }

    @Override
    public UserDto getUser(String id) {
        return userClient.findById(id).getBody();
    }

    @Override
    public boolean verifyTokenWithId(String id) {
//      Add custom exception later.
        try {
            return tokenServiceImpl.verifyIfTokenIsStored(id);
        }catch(RuntimeException e){
            throw new RuntimeException();
        }
    }
}
