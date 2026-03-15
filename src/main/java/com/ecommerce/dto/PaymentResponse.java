package com.ecommerce.dto;

import com.ecommerce.enums.PaymentMethod;
import com.ecommerce.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Payment resource returned by the API")
public record PaymentResponse(
    @Schema(description = "Unique identifier of the payment", example = "1")
    Long id,

    @Schema(description = "ID of the order this payment belongs to", example = "1")
    Long orderId,

    @Schema(description = "Payment method: CREDIT_CARD, DEBIT_CARD, BOLETO, PIX, PAYPAL", example = "CREDIT_CARD")
    PaymentMethod method,

    @Schema(description = "Payment status: PENDING, APPROVED, DENIED, CANCELLED, REFUNDED", example = "APPROVED")
    PaymentStatus status,

    @Schema(description = "Payment amount", example = "239.99")
    BigDecimal amount,

    @Schema(description = "Number of installments", example = "3")
    Integer installments,

    @Schema(description = "Payment gateway identifier", example = "stripe")
    String gateway,

    @Schema(description = "Transaction ID returned by the payment gateway", example = "pi_3OmXXX")
    String gatewayTransactionId,

    @Schema(description = "Timestamp when the payment was confirmed (ISO 8601)", example = "2024-01-15T10:45:00")
    LocalDateTime paidAt
) {}
