package com.microservices.auth.domain.ports.outbound.user;

import com.microservices.auth.domain.model.user.UserAuthDomain;

import java.util.List;
import java.util.Optional;

public interface AuthUserRepositoryPort {

    UserAuthDomain save(UserAuthDomain userAuthDomain);

    Optional<UserAuthDomain> findByEmail(String email);

    Optional<UserAuthDomain> findById(String userId);
    
    List<UserAuthDomain> findAll();

}
