package com.ecommerce.dto;

public record TechnicalSpecificationRequest(
    Long productId,
    String specKey,
    String value
) {}
