package com.ecommerce.service;

import com.ecommerce.dto.InventoryMovementRequest;
import com.ecommerce.dto.InventoryMovementResponse;
import com.ecommerce.entity.InventoryMovement;
import com.ecommerce.entity.Variant;
import com.ecommerce.repository.InventoryMovementRepository;
import com.ecommerce.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryMovementService {

    private final InventoryMovementRepository repository;
    private final VariantRepository variantRepository;

    public List<InventoryMovementResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public InventoryMovementResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public InventoryMovementResponse create(InventoryMovementRequest dto) {
        Variant variant = variantRepository.findById(dto.variantId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found"));
        InventoryMovement entity = InventoryMovement.builder()
            .variant(variant)
            .type(dto.type())
            .quantity(dto.quantity())
            .reason(dto.reason())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public InventoryMovementResponse update(Long id, InventoryMovementRequest dto) {
        InventoryMovement entity = getOrThrow(id);
        Variant variant = variantRepository.findById(dto.variantId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found"));
        entity.setVariant(variant);
        entity.setType(dto.type());
        entity.setQuantity(dto.quantity());
        entity.setReason(dto.reason());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private InventoryMovement getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "InventoryMovement not found: " + id));
    }

    private InventoryMovementResponse toResponse(InventoryMovement e) {
        return new InventoryMovementResponse(e.getId(), e.getVariant().getId(), e.getType(),
            e.getQuantity(), e.getReason(), e.getCreatedAt());
    }
}
