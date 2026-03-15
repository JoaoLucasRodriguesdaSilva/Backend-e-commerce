package com.ecommerce.dto;

import com.ecommerce.enums.ReturnStatus;
import com.ecommerce.enums.ReturnType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Request payload for creating or updating an order return")
public record OrderReturnRequest(
    @Schema(description = "ID of the order being returned", example = "1")
    Long orderId,

    @Schema(description = "Reason for the return", example = "Product arrived damaged")
    String reason,

    @Schema(description = "Return status: REQUESTED, APPROVED, REJECTED, COMPLETED", example = "REQUESTED")
    ReturnStatus status,

    @Schema(description = "Return type: FULL or PARTIAL", example = "FULL")
    ReturnType type,

    @Schema(description = "Timestamp when the return was approved (ISO 8601, null if pending)", example = "2024-01-26T10:00:00")
    LocalDateTime approvedAt
) {}
