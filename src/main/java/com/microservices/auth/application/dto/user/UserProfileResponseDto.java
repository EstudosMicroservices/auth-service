package com.microservices.auth.application.dto.user;

import java.time.LocalDate;

public record UserProfileResponseDto(
        String id,
        String email,
        String nomeCompleto,
        LocalDate dataNascimento,
        String rua,
        String bairro,
        String cidade,
        String estado
) {
}
