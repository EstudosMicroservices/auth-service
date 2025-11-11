package com.microservices.auth.infrastructure.adapters.outbound.user.authentication;

import com.microservices.auth.application.mappers.AuthUserMapper;
import com.microservices.auth.domain.model.user.UserAuthDomain;
import com.microservices.auth.domain.ports.outbound.user.AuthUserRepositoryPort;
import com.microservices.auth.infrastructure.persistence.entity.UserEntity;
import com.microservices.auth.infrastructure.persistence.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUserRepositoryAdapter implements AuthUserRepositoryPort {

    private final AuthUserMapper authUserMapper;
    private final AuthUserRepository authUserRepository;
    @Override
    public UserAuthDomain save(UserAuthDomain userAuthDomain) {
        UserEntity entityUser = authUserMapper.toEntity(userAuthDomain);
        UserEntity savedUser = authUserRepository.save(entityUser);
        return authUserMapper.toAuthDomain(savedUser);
    }

    @Override
    public Optional<UserAuthDomain> findByEmail(String email) {
        Optional<UserEntity> userFound = authUserRepository.findByEmail(email);
        return userFound.map(authUserMapper::toAuthDomain);
    }

    @Override
    public Optional<UserAuthDomain> findById(String userId){
        Optional<UserEntity> userFound = authUserRepository.findById(userId);
        return userFound.map(authUserMapper::toAuthDomain);
    }

    @Override
    public List<UserAuthDomain> findAll() {
        List<UserEntity> listUserEntity = authUserRepository.findAll();
        return listUserEntity.stream().map(authUserMapper::toAuthDomain).toList();
    }
}
