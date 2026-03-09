package com.ecommerce.dto;

import com.ecommerce.enums.ShipmentService;
import com.ecommerce.enums.ShipmentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ShipmentResponse(
    Long id,
    Long orderId,
    String carrier,
    ShipmentService service,
    String trackingCode,
    ShipmentStatus status,
    BigDecimal cost,
    Integer estimatedDays,
    LocalDateTime shippedAt,
    LocalDateTime deliveredAt
) {}
