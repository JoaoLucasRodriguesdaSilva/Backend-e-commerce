package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Shopping cart resource returned by the API")
public record CartResponse(
    @Schema(description = "Unique identifier of the cart", example = "1")
    Long id,

    @Schema(description = "ID of the customer who owns this cart", example = "1")
    Long customerId,

    @Schema(description = "Timestamp when the cart was created (ISO 8601)", example = "2024-01-15T10:30:00")
    LocalDateTime createdAt,

    @Schema(description = "Timestamp of the last cart update (ISO 8601)", example = "2024-01-15T11:00:00")
    LocalDateTime updatedAt
) {}
