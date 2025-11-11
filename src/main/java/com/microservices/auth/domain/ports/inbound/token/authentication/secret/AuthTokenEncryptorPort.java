package com.microservices.auth.domain.ports.inbound.token.authentication.secret;

import com.auth0.jwt.algorithms.Algorithm;

public interface AuthTokenEncryptorPort {

    Algorithm encryptor(String jjwtSecret);

}
