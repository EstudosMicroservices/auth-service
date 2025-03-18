package com.microservices.auth.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthRequest {

    private String id;
    private String senha;
}
