package com.ecommerce.dto;

public record TechnicalSpecificationResponse(
    Long id,
    Long productId,
    String specKey,
    String value
) {}
