package com.ecommerce.dto;

import com.ecommerce.enums.ShipmentService;
import com.ecommerce.enums.ShipmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Shipment resource returned by the API")
public record ShipmentResponse(
    @Schema(description = "Unique identifier of the shipment", example = "1")
    Long id,

    @Schema(description = "ID of the parent order", example = "1")
    Long orderId,

    @Schema(description = "Name of the shipping carrier", example = "FedEx")
    String carrier,

    @Schema(description = "Shipping service tier: STANDARD, EXPRESS, PRIORITY", example = "EXPRESS")
    ShipmentService service,

    @Schema(description = "Carrier tracking code", example = "FX123456789BR")
    String trackingCode,

    @Schema(description = "Shipment status: PENDING, SHIPPED, IN_TRANSIT, DELIVERED, LOST, RETURNED", example = "IN_TRANSIT")
    ShipmentStatus status,

    @Schema(description = "Shipping cost", example = "15.00")
    BigDecimal cost,

    @Schema(description = "Estimated delivery in business days", example = "3")
    Integer estimatedDays,

    @Schema(description = "Timestamp when the package was shipped (ISO 8601)", example = "2024-01-16T08:00:00")
    LocalDateTime shippedAt,

    @Schema(description = "Timestamp when the package was delivered (ISO 8601)", example = "2024-01-19T14:30:00")
    LocalDateTime deliveredAt
) {}
