package com.ecommerce.dto;

import java.time.LocalDate;

public record WarrantyRequest(
    Long orderItemId,
    String serialNumber,
    LocalDate startDate,
    LocalDate endDate,
    String status
) {}
