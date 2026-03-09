package com.ecommerce.dto;

import java.time.LocalDate;

public record CustomerRequest(
    String name,
    String email,
    String taxId,
    String phone,
    LocalDate birthDate
) {}
