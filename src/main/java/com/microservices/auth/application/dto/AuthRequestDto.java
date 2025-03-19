package com.microservices.auth.application.dto;

public record AuthRequestDto(
        String id,
        String senha
) {
}
