package com.microservices.auth.application.dto.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisteredEvent {
    private String id;
    private String email;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
}