package com.ecommerce.dto;

import com.ecommerce.enums.VariantAttribute;
import java.math.BigDecimal;

public record VariantRequest(
    Long productId,
    VariantAttribute attribute,
    String value,
    BigDecimal extraPrice,
    String variantSku
) {}
