package com.microservices.auth.application.implementations.user.authentication;

import com.microservices.auth.application.dto.events.UserRegisteredEvent;
import com.microservices.auth.application.exceptions.custom.user.UserProfileAlreadyExistsException;
import com.microservices.auth.application.mappers.AuthUserMapper;
import com.microservices.auth.domain.model.user.UserAuthDomain;
import com.microservices.auth.domain.model.user.UserRegistrationDomain;
import com.microservices.auth.domain.ports.inbound.token.authentication.generate.AuthTokenGeneratePort;
import com.microservices.auth.domain.ports.inbound.user.authentication.UserRegisterPort;
import com.microservices.auth.domain.ports.outbound.token.authentication.store.AuthTokenStorePort;
import com.microservices.auth.domain.ports.outbound.user.AuthUserRepositoryPort;
import com.microservices.auth.domain.ports.outbound.user.password.PasswordEncodingPort;
import com.microservices.auth.infrastructure.client.UserWebClient;
import com.microservices.auth.infrastructure.messaging.UserEventPublisher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisterImpl implements UserRegisterPort {

    private static final Logger log = LoggerFactory.getLogger(UserRegisterImpl.class);
    private final AuthUserMapper authUserMapper;
    private final PasswordEncodingPort passwordEncodingPort;
    private final AuthUserRepositoryPort authUserRepositoryPort;
    private final UserEventPublisher userEventPublisher;
    private final AuthTokenGeneratePort authTokenGeneratePort;
    private final AuthTokenStorePort authTokenStorePort;
    private final UserWebClient userWebClient;

    @Override
    public String registerUser(UserRegistrationDomain userRegistrationDomain) {
        log.info("[UserRegisterImpl] Starting User registration process!");

        userWebClient.findByEmail(userRegistrationDomain.getEmail()).ifPresent(userDto -> {
           log.error("[UserRegisterImpl] User profile already exists!");
           throw new UserProfileAlreadyExistsException("User profile already exists!");
        });

        UserAuthDomain userAuthToSave = authUserMapper.toAuthDomain(userRegistrationDomain);
        userAuthToSave.setSenha(passwordEncodingPort.encode(userAuthToSave.getSenha()));
        UserAuthDomain savedUser = authUserRepositoryPort.save(userAuthToSave);
        userRegistrationDomain.setId(savedUser.getId());

        UserRegisteredEvent userEvent = authUserMapper.toEvent(userRegistrationDomain);

        userEventPublisher.publishUserRegistered(userEvent);
        String token = authTokenGeneratePort.generateToken(savedUser.getEmail(), savedUser.getRoles());
        authTokenStorePort.storeToken(savedUser.getEmail(), token, 24 * 3600);

        return token;

    }
}
