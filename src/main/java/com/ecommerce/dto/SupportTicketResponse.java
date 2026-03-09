package com.ecommerce.dto;

import com.ecommerce.enums.SupportChannel;
import com.ecommerce.enums.TicketStatus;
import java.time.LocalDateTime;

public record SupportTicketResponse(
    Long id,
    Long customerId,
    Long orderId,
    String subject,
    TicketStatus status,
    SupportChannel channel,
    LocalDateTime createdAt
) {}
