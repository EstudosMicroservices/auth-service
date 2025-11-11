package com.microservices.auth.application.mappers;

import com.microservices.auth.application.dto.events.UserRegisteredEvent;
import com.microservices.auth.application.dto.user.UserAuthenticationDto;
import com.microservices.auth.application.dto.user.UserRegistrationDto;
import com.microservices.auth.domain.model.user.UserAuthDomain;
import com.microservices.auth.domain.model.user.UserRegistrationDomain;
import com.microservices.auth.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", expression = "java(java.util.Set.of(com.microservices.auth.domain.model.roles.UserRoles.USER))") // Assign default role
    @Mapping(target = "email", source = "email")
    UserAuthDomain toAuthDomain(UserRegistrationDomain registrationDomain);

    @Mapping(target = "id", ignore = true) // Gera ID
    @Mapping(target = "senha", source = "senha")
    UserRegistrationDomain toRegisterDomain(UserRegistrationDto registrationDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "senha", source = "senha")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "email", source = "email")
    UserAuthDomain toAuthDomain(UserAuthenticationDto authenticationDto);

    UserAuthDomain toAuthDomain(UserEntity userEntity);

    UserAuthenticationDto toAuthDto(UserAuthDomain userAuthDomain);

    UserEntity toEntity(UserAuthDomain userAuthDomain);


    UserRegisteredEvent toEvent (UserRegistrationDomain userRegistrationDomain);

}
