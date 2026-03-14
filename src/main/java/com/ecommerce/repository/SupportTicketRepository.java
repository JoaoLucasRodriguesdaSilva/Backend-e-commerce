package com.ecommerce.repository;

import com.ecommerce.entity.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    List<SupportTicket> findByCustomer_Email(String email);
    Optional<SupportTicket> findByIdAndCustomer_Email(Long id, String email);
}
