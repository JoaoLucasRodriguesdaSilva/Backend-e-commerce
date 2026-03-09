package com.ecommerce.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CustomerResponse(
    Long id,
    String name,
    String email,
    String taxId,
    String phone,
    LocalDate birthDate,
    LocalDateTime createdAt
) {}
