package com.microservices.auth.domain.ports.inbound.token.authentication.generate;

import com.microservices.auth.domain.model.roles.UserRoles;

import java.util.Set;

public interface AuthTokenGeneratePort {

    String generateToken(String userId, Set<UserRoles> roles);
}
