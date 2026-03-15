package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Customer profile returned by the API")
public record CustomerResponse(
    @Schema(description = "Unique identifier of the customer", example = "1")
    Long id,

    @Schema(description = "Full name of the customer", example = "João Lucas")
    String name,

    @Schema(description = "E-mail address", example = "joao@example.com")
    String email,

    @Schema(description = "Tax identification number (CPF/CNPJ)", example = "123.456.789-00")
    String taxId,

    @Schema(description = "Phone number", example = "+55 11 91234-5678")
    String phone,

    @Schema(description = "Date of birth (ISO 8601)", example = "1995-07-20")
    LocalDate birthDate,

    @Schema(description = "Timestamp when the customer was created (ISO 8601)", example = "2024-01-15T10:30:00")
    LocalDateTime createdAt
) {}
