package com.ecommerce.repository;

import com.ecommerce.entity.SavedCardToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedCardTokenRepository extends JpaRepository<SavedCardToken, Long> {
}
