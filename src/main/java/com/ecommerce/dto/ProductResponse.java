package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Product resource returned by the API")
public record ProductResponse(
    @Schema(description = "Unique identifier of the product", example = "1")
    Long id,

    @Schema(description = "Product name", example = "Wireless Headphones Pro")
    String name,

    @Schema(description = "Short marketing description", example = "Premium over-ear headphones with ANC")
    String description,

    @Schema(description = "Detailed technical description", example = "40mm drivers, 30-hour battery, USB-C charging")
    String technicalDescription,

    @Schema(description = "Regular list price", example = "299.99")
    BigDecimal basePrice,

    @Schema(description = "Current promotional price (null if no promotion)", example = "249.99")
    BigDecimal promotionalPrice,

    @Schema(description = "Unique stock-keeping unit code", example = "WHP-PRO-001")
    String sku,

    @Schema(description = "Brand name", example = "SoundMax")
    String brand,

    @Schema(description = "Model identifier", example = "PRO-X1")
    String model,

    @Schema(description = "Warranty duration in months", example = "12")
    Integer warrantyMonths,

    @Schema(description = "Weight in kilograms", example = "0.35")
    BigDecimal weight,

    @Schema(description = "Dimensions as LxWxH string", example = "20x18x8 cm")
    String dimensions,

    @Schema(description = "Whether the product is active / visible", example = "true")
    Boolean active,

    @Schema(description = "Timestamp when the product was created (ISO 8601)", example = "2024-01-15T10:30:00")
    LocalDateTime createdAt
) {}
