package com.microservices.auth.domain.ports.outbound.token.authentication.secret;

import javax.crypto.SecretKey;

public interface AuthTokenSecretPort {

    SecretKey getSecretKey();

}
