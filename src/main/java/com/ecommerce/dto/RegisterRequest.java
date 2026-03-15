package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Request payload for new user registration")
public record RegisterRequest(
        @Schema(description = "Full name of the user", example = "João Lucas")
        @NotBlank String name,

        @Schema(description = "E-mail address (used as login)", example = "joao@example.com")
        @NotBlank @Email String email,

        @Schema(description = "Password (minimum 6 characters)", example = "secret123")
        @NotBlank @Size(min = 6) String password,

        @Schema(description = "Tax identification number (CPF/CNPJ)", example = "123.456.789-00")
        String taxId,

        @Schema(description = "Phone number", example = "+55 11 91234-5678")
        String phone,

        @Schema(description = "Date of birth (ISO 8601)", example = "1995-07-20")
        LocalDate birthDate
) {}
