package com.ecommerce.dto;

public record CategoryRequest(
    String name,
    String slug,
    Long parentCategoryId,
    String icon,
    Integer displayOrder
) {}
