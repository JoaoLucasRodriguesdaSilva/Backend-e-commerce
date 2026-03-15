package com.ecommerce.dto;

import com.ecommerce.enums.SupportChannel;
import com.ecommerce.enums.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Support ticket resource returned by the API")
public record SupportTicketResponse(
    @Schema(description = "Unique identifier of the ticket", example = "1")
    Long id,

    @Schema(description = "ID of the customer who opened the ticket", example = "1")
    Long customerId,

    @Schema(description = "ID of the related order (null if not order-related)", example = "1")
    Long orderId,

    @Schema(description = "Brief subject describing the issue", example = "Order not received after 10 days")
    String subject,

    @Schema(description = "Ticket status: OPEN, PENDING, RESOLVED, CLOSED", example = "OPEN")
    TicketStatus status,

    @Schema(description = "Contact channel: EMAIL, CHAT, PHONE, SOCIAL", example = "EMAIL")
    SupportChannel channel,

    @Schema(description = "Timestamp when the ticket was opened (ISO 8601)", example = "2024-01-25T09:00:00")
    LocalDateTime createdAt
) {}
