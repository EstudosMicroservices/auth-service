package com.microservices.auth.application.dto;

import jakarta.validation.constraints.NotBlank;

public record SessionDto(
        @NotBlank
        String token
) {
}
