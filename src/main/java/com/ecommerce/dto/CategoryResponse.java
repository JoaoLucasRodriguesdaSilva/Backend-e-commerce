package com.ecommerce.dto;

public record CategoryResponse(
    Long id,
    String name,
    String slug,
    Long parentCategoryId,
    String icon,
    Integer displayOrder
) {}
