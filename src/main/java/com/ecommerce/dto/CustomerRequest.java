package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Request payload for creating or updating a customer profile")
public record CustomerRequest(
    @Schema(description = "Full name of the customer", example = "João Lucas")
    String name,

    @Schema(description = "E-mail address", example = "joao@example.com")
    String email,

    @Schema(description = "Tax identification number (CPF/CNPJ)", example = "123.456.789-00")
    String taxId,

    @Schema(description = "Phone number", example = "+55 11 91234-5678")
    String phone,

    @Schema(description = "Date of birth (ISO 8601)", example = "1995-07-20")
    LocalDate birthDate
) {}
