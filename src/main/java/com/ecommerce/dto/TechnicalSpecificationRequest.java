package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for creating or updating a technical specification")
public record TechnicalSpecificationRequest(
    @Schema(description = "ID of the product this specification belongs to", example = "1")
    Long productId,

    @Schema(description = "Specification attribute key", example = "Connectivity")
    String specKey,

    @Schema(description = "Specification attribute value", example = "Bluetooth 5.3")
    String value
) {}
