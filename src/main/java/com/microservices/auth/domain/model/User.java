package com.microservices.auth.domain.model;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class User {

    private String id;
    private String nomeCompleto;
    private String email;
    private String senha;
    private String dataNascimento;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;

}
