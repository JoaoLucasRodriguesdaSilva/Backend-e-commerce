package com.ecommerce.repository;

import com.ecommerce.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByOrder_Customer_Email(String email);
    Optional<Payment> findByIdAndOrder_Customer_Email(Long id, String email);
}
