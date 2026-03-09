package com.ecommerce.service;

import com.ecommerce.dto.WarrantyRequest;
import com.ecommerce.dto.WarrantyResponse;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Warranty;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.WarrantyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarrantyService {

    private final WarrantyRepository repository;
    private final OrderItemRepository orderItemRepository;

    public List<WarrantyResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public WarrantyResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public WarrantyResponse create(WarrantyRequest dto) {
        OrderItem orderItem = orderItemRepository.findById(dto.orderItemId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "OrderItem not found"));
        Warranty entity = Warranty.builder()
            .orderItem(orderItem)
            .serialNumber(dto.serialNumber())
            .startDate(dto.startDate())
            .endDate(dto.endDate())
            .status(dto.status())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public WarrantyResponse update(Long id, WarrantyRequest dto) {
        Warranty entity = getOrThrow(id);
        OrderItem orderItem = orderItemRepository.findById(dto.orderItemId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "OrderItem not found"));
        entity.setOrderItem(orderItem);
        entity.setSerialNumber(dto.serialNumber());
        entity.setStartDate(dto.startDate());
        entity.setEndDate(dto.endDate());
        entity.setStatus(dto.status());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Warranty getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Warranty not found: " + id));
    }

    private WarrantyResponse toResponse(Warranty e) {
        return new WarrantyResponse(e.getId(), e.getOrderItem().getId(), e.getSerialNumber(),
            e.getStartDate(), e.getEndDate(), e.getStatus());
    }
}
