package com.microservices.auth.domain.model;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ResetPassToken {

    private String idUser;

    private String token;

    private Instant expiration;


}
