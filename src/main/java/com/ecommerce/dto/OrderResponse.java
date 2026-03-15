package com.ecommerce.dto;

import com.ecommerce.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Order resource returned by the API")
public record OrderResponse(
    @Schema(description = "Unique identifier of the order", example = "1")
    Long id,

    @Schema(description = "ID of the customer who placed the order", example = "1")
    Long customerId,

    @Schema(description = "Order status: PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED", example = "PENDING")
    OrderStatus status,

    @Schema(description = "Sum of all item prices before discounts and shipping", example = "249.99")
    BigDecimal subtotal,

    @Schema(description = "Discount amount applied", example = "25.00")
    BigDecimal discount,

    @Schema(description = "Shipping cost", example = "15.00")
    BigDecimal shipping,

    @Schema(description = "Final order total", example = "239.99")
    BigDecimal total,

    @Schema(description = "Timestamp when the order was created (ISO 8601)", example = "2024-01-15T10:30:00")
    LocalDateTime createdAt
) {}
