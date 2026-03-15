package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload for user authentication")
public record LoginRequest(
        @Schema(description = "Registered e-mail address", example = "user@example.com")
        @NotBlank @Email String email,

        @Schema(description = "Account password", example = "secret123")
        @NotBlank String password
) {}
