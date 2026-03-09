package com.ecommerce.dto;

import com.ecommerce.enums.SupportChannel;
import com.ecommerce.enums.TicketStatus;

public record SupportTicketRequest(
    Long customerId,
    Long orderId,
    String subject,
    TicketStatus status,
    SupportChannel channel
) {}
