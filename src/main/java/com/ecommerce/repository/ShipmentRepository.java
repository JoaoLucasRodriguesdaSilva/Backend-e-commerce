package com.ecommerce.repository;

import com.ecommerce.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByOrder_Customer_Email(String email);
    Optional<Shipment> findByIdAndOrder_Customer_Email(Long id, String email);
}
