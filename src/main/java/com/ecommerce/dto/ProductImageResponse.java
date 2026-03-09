package com.ecommerce.dto;

public record ProductImageResponse(
    Long id,
    Long productId,
    String url,
    String alt,
    Integer displayOrder,
    Boolean isPrimary
) {}
