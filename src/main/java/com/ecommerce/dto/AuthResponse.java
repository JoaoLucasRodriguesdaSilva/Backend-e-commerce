package com.ecommerce.dto;

public record AuthResponse(String token, String tokenType, String email, String name) {

    public AuthResponse(String token, String email, String name) {
        this(token, "Bearer", email, name);
    }
}
