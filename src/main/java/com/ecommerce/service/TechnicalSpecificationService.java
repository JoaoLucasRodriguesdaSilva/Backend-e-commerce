package com.ecommerce.service;

import com.ecommerce.dto.TechnicalSpecificationRequest;
import com.ecommerce.dto.TechnicalSpecificationResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.TechnicalSpecification;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.TechnicalSpecificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnicalSpecificationService {

    private final TechnicalSpecificationRepository repository;
    private final ProductRepository productRepository;

    public List<TechnicalSpecificationResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public TechnicalSpecificationResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public TechnicalSpecificationResponse create(TechnicalSpecificationRequest dto) {
        Product product = productRepository.findById(dto.productId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        TechnicalSpecification entity = TechnicalSpecification.builder()
            .product(product)
            .specKey(dto.specKey())
            .value(dto.value())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public TechnicalSpecificationResponse update(Long id, TechnicalSpecificationRequest dto) {
        TechnicalSpecification entity = getOrThrow(id);
        Product product = productRepository.findById(dto.productId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        entity.setProduct(product);
        entity.setSpecKey(dto.specKey());
        entity.setValue(dto.value());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private TechnicalSpecification getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TechnicalSpecification not found: " + id));
    }

    private TechnicalSpecificationResponse toResponse(TechnicalSpecification e) {
        return new TechnicalSpecificationResponse(e.getId(), e.getProduct().getId(), e.getSpecKey(), e.getValue());
    }
}
