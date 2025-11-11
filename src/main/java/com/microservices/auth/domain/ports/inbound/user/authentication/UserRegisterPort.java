package com.microservices.auth.domain.ports.inbound.user.authentication;

import com.microservices.auth.domain.model.user.UserRegistrationDomain;

public interface UserRegisterPort {

    String registerUser(UserRegistrationDomain userRegistrationDomain);

}
