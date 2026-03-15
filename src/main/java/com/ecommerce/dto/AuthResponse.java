package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response containing a JWT token")
public record AuthResponse(
        @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token,

        @Schema(description = "Token type, always 'Bearer'", example = "Bearer")
        String tokenType,

        @Schema(description = "E-mail of the authenticated user", example = "user@example.com")
        String email,

        @Schema(description = "Name of the authenticated user", example = "João Lucas")
        String name
) {

    public AuthResponse(String token, String email, String name) {
        this(token, "Bearer", email, name);
    }
}
