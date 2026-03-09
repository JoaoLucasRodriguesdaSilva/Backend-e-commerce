package com.ecommerce.dto;

import com.ecommerce.enums.WarrantyStatus;
import java.time.LocalDate;

public record WarrantyRequest(
    Long orderItemId,
    String serialNumber,
    LocalDate startDate,
    LocalDate endDate,
    WarrantyStatus status
) {}
