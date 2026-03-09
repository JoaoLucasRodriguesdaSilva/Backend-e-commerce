package com.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
    Long id,
    String name,
    String description,
    String technicalDescription,
    BigDecimal basePrice,
    BigDecimal promotionalPrice,
    String sku,
    String brand,
    String model,
    Integer warrantyMonths,
    BigDecimal weight,
    String dimensions,
    Boolean active,
    LocalDateTime createdAt
) {}
