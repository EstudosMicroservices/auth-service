package com.microservices.auth.domain.model.user;

import com.microservices.auth.domain.model.roles.UserRoles;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserAuthDomain {

    private String id;
    private String email;
    private String senha;
    private Set<UserRoles> roles;

}
