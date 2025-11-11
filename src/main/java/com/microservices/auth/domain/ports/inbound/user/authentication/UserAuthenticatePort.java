package com.microservices.auth.domain.ports.inbound.user.authentication;

import com.microservices.auth.domain.model.user.UserAuthDomain;

public interface UserAuthenticatePort {

    String authenticate(UserAuthDomain userAuthDomain);

}
