package com.microservices.auth.domain.ports.inbound.user.authentication;

public interface UserLogoutPort {

    void logout(String token);

}
