package com.ecommerce.dto;

import com.ecommerce.enums.SupportChannel;
import com.ecommerce.enums.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for creating or updating a support ticket")
public record SupportTicketRequest(
    @Schema(description = "ID of the customer opening the ticket", example = "1")
    Long customerId,

    @Schema(description = "ID of the related order (optional)", example = "1")
    Long orderId,

    @Schema(description = "Brief subject describing the issue", example = "Order not received after 10 days")
    String subject,

    @Schema(description = "Ticket status: OPEN, PENDING, RESOLVED, CLOSED", example = "OPEN")
    TicketStatus status,

    @Schema(description = "Contact channel: EMAIL, CHAT, PHONE, SOCIAL", example = "EMAIL")
    SupportChannel channel
) {}
