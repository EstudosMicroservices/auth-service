package com.microservices.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UserDto(
        @NotBlank(message = "This field cannot be null or blank!")
        String id,

        @NotBlank(message = "This field cannot be null or blank!")
        @Pattern(regexp = "^[a-zA-Z ]{1,100}$", message = "This field must contain only letters and space.")
        String nomeCompleto,

        @NotBlank(message = "This field cannot be null or blank!")
        @Pattern(regexp = "^[A-Za-z0-9+_.-]{1,20}@[A-Za-z0-9.-]{1,20}\\.[A-Za-z]{2,10}$", message = "Must be an email pattern.")
        String email,

        @NotBlank(message = "This field cannot be null or blank!")
        String senha,

        @NotNull(message = "'This field cannot be null or blank!")
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dataNascimento,

        @NotBlank(message = "This field cannot be null or blank!")
        @Pattern(regexp = "^[a-zA-Z ]{1,50}$", message = "This field must contain only letters and space.")
        String rua,

        @NotBlank(message = "This field cannot be null or blank!")
        @Pattern(regexp = "^[a-zA-Z ]{1,30}$", message = "This field must contain only letters and space.")
        String bairro,

        @NotBlank(message = "This field cannot be null or blank!")
        @Pattern(regexp = "^[a-zA-Z ]{1,30}$", message = "This field must contain only letters and space.")
        String cidade,

        @NotBlank(message = "This field cannot be null or blank!")
        @Pattern(regexp = "^[a-zA-Z ]{1,30}$", message = "This field must contain only letters and space.")
        String estado
) {
}
