package com.ecommerce.dto;

import com.ecommerce.enums.WarrantyStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Request payload for creating or updating a warranty record")
public record WarrantyRequest(
    @Schema(description = "ID of the order item covered by this warranty", example = "1")
    Long orderItemId,

    @Schema(description = "Serial number of the product unit", example = "SN-2024-00123")
    String serialNumber,

    @Schema(description = "Warranty start date (ISO 8601)", example = "2024-01-15")
    LocalDate startDate,

    @Schema(description = "Warranty end date (ISO 8601)", example = "2025-01-15")
    LocalDate endDate,

    @Schema(description = "Warranty status: ACTIVE, CLAIMED, EXPIRED", example = "ACTIVE")
    WarrantyStatus status
) {}
