package com.ecommerce.dto;

import com.ecommerce.enums.WarrantyStatus;
import java.time.LocalDate;

public record WarrantyResponse(
    Long id,
    Long orderItemId,
    String serialNumber,
    LocalDate startDate,
    LocalDate endDate,
    WarrantyStatus status
) {}
