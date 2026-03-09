package com.ecommerce.dto;

import java.time.LocalDate;

public record WarrantyResponse(
    Long id,
    Long orderItemId,
    String serialNumber,
    LocalDate startDate,
    LocalDate endDate,
    String status
) {}
