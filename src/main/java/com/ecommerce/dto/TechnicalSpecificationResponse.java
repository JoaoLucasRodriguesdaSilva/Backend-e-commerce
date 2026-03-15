package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Technical specification resource returned by the API")
public record TechnicalSpecificationResponse(
    @Schema(description = "Unique identifier of the specification", example = "1")
    Long id,

    @Schema(description = "ID of the parent product", example = "1")
    Long productId,

    @Schema(description = "Specification attribute key", example = "Connectivity")
    String specKey,

    @Schema(description = "Specification attribute value", example = "Bluetooth 5.3")
    String value
) {}
