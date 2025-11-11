package com.microservices.auth.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAuthenticationDto(
        @NotBlank(message = "This field cannot be null or blank!")
        @Email
        String email,

        @NotBlank(message = "This field cannot be null or blank!")
        String senha
) {
}
