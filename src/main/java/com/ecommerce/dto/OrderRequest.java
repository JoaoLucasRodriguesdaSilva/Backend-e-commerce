package com.ecommerce.dto;

import com.ecommerce.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Request payload for creating or updating an order")
public record OrderRequest(
    @Schema(description = "ID of the customer placing the order", example = "1")
    Long customerId,

    @Schema(description = "Order status: PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED", example = "PENDING")
    OrderStatus status,

    @Schema(description = "Sum of all item prices before discounts and shipping", example = "249.99")
    BigDecimal subtotal,

    @Schema(description = "Discount amount applied (e.g. from a coupon)", example = "25.00")
    BigDecimal discount,

    @Schema(description = "Shipping cost", example = "15.00")
    BigDecimal shipping,

    @Schema(description = "Final order total (subtotal - discount + shipping)", example = "239.99")
    BigDecimal total
) {}
