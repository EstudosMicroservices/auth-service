package com.microservices.auth.domain.ports.inbound.user.query;

import com.microservices.auth.application.dto.user.UserAuthenticationDto;

import java.util.List;

public interface FindAllAuthUsersPort{

    List<UserAuthenticationDto> findAll();
}
