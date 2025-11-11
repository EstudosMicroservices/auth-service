package com.microservices.auth.application.implementations.user.query;

import com.microservices.auth.application.dto.user.UserAuthenticationDto;
import com.microservices.auth.application.exceptions.custom.user.UserAlreadyAuthenticatedException;
import com.microservices.auth.application.mappers.AuthUserMapper;
import com.microservices.auth.domain.model.user.UserAuthDomain;
import com.microservices.auth.domain.ports.inbound.user.query.FindAllAuthUsersPort;
import com.microservices.auth.domain.ports.outbound.user.AuthUserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAllAuthUsersImpl implements FindAllAuthUsersPort {

    private final AuthUserRepositoryPort authUserRepositoryPort;
    private final AuthUserMapper authUserMapper;

    @Override
    public List<UserAuthenticationDto> findAll() {
        List<UserAuthDomain> listAuthUsers = authUserRepositoryPort.findAll();
        if(listAuthUsers.isEmpty()){
            throw new UserAlreadyAuthenticatedException("List is empty");
        }
        return listAuthUsers.stream().map(authUserMapper::toAuthDto).toList();
    }
}
