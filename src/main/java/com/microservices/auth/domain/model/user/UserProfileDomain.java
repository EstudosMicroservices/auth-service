package com.microservices.auth.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDomain {

    private String id;
    private String nomeCompleto;
    private String email;
    private LocalDate dataNascimento;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;

}
