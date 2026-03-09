package com.ecommerce.dto;

import java.math.BigDecimal;

public record ProductRequest(
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
    Boolean active
) {}
