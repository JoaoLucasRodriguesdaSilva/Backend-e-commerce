package com.ecommerce.service;

import com.ecommerce.dto.InventoryRequest;
import com.ecommerce.dto.InventoryResponse;
import com.ecommerce.entity.Inventory;
import com.ecommerce.entity.Variant;
import com.ecommerce.repository.InventoryRepository;
import com.ecommerce.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository repository;
    private final VariantRepository variantRepository;

    public List<InventoryResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public InventoryResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public InventoryResponse create(InventoryRequest dto) {
        Variant variant = variantRepository.findById(dto.variantId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found"));
        Inventory entity = Inventory.builder()
            .variant(variant)
            .quantity(dto.quantity())
            .reservedQuantity(dto.reservedQuantity())
            .reorderPoint(dto.reorderPoint())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public InventoryResponse update(Long id, InventoryRequest dto) {
        Inventory entity = getOrThrow(id);
        Variant variant = variantRepository.findById(dto.variantId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found"));
        entity.setVariant(variant);
        entity.setQuantity(dto.quantity());
        entity.setReservedQuantity(dto.reservedQuantity());
        entity.setReorderPoint(dto.reorderPoint());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Inventory getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found: " + id));
    }

    private InventoryResponse toResponse(Inventory e) {
        return new InventoryResponse(e.getId(), e.getVariant().getId(), e.getQuantity(),
            e.getReservedQuantity(), e.getReorderPoint());
    }
}
