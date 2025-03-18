package com.microservices.auth.domain.ports.outbound;

import com.microservices.auth.domain.model.AuthRequest;

public interface AuthRepositoryPort {

    String authenticate(AuthRequest authRequest);
}
