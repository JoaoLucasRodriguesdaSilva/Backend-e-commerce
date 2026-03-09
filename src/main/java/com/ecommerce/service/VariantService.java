package com.ecommerce.service;

import com.ecommerce.dto.VariantRequest;
import com.ecommerce.dto.VariantResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Variant;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariantService {

    private final VariantRepository repository;
    private final ProductRepository productRepository;

    public List<VariantResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public VariantResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public VariantResponse create(VariantRequest dto) {
        Product product = productRepository.findById(dto.productId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        Variant entity = Variant.builder()
            .product(product)
            .attribute(dto.attribute())
            .value(dto.value())
            .extraPrice(dto.extraPrice())
            .variantSku(dto.variantSku())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public VariantResponse update(Long id, VariantRequest dto) {
        Variant entity = getOrThrow(id);
        Product product = productRepository.findById(dto.productId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        entity.setProduct(product);
        entity.setAttribute(dto.attribute());
        entity.setValue(dto.value());
        entity.setExtraPrice(dto.extraPrice());
        entity.setVariantSku(dto.variantSku());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Variant getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found: " + id));
    }

    private VariantResponse toResponse(Variant e) {
        return new VariantResponse(e.getId(), e.getProduct().getId(), e.getAttribute(), e.getValue(),
            e.getExtraPrice(), e.getVariantSku());
    }
}
