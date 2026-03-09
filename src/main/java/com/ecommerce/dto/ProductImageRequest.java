package com.ecommerce.dto;

public record ProductImageRequest(
    Long productId,
    String url,
    String alt,
    Integer displayOrder,
    Boolean isPrimary
) {}
