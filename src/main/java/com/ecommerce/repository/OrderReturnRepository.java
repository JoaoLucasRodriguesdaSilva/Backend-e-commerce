package com.ecommerce.repository;

import com.ecommerce.entity.OrderReturn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderReturnRepository extends JpaRepository<OrderReturn, Long> {
    List<OrderReturn> findByOrder_Customer_Email(String email);
    Optional<OrderReturn> findByIdAndOrder_Customer_Email(Long id, String email);
}
