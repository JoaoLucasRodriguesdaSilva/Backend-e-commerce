package com.ecommerce.service;

import com.ecommerce.dto.PromotionRequest;
import com.ecommerce.dto.PromotionResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Promotion;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository repository;
    private final ProductRepository productRepository;

    public List<PromotionResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public PromotionResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public PromotionResponse create(PromotionRequest dto) {
        Product product = productRepository.findById(dto.productId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        Promotion entity = Promotion.builder()
            .product(product)
            .promotionalPrice(dto.promotionalPrice())
            .startsAt(dto.startsAt())
            .endsAt(dto.endsAt())
            .featuredHome(dto.featuredHome())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public PromotionResponse update(Long id, PromotionRequest dto) {
        Promotion entity = getOrThrow(id);
        Product product = productRepository.findById(dto.productId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        entity.setProduct(product);
        entity.setPromotionalPrice(dto.promotionalPrice());
        entity.setStartsAt(dto.startsAt());
        entity.setEndsAt(dto.endsAt());
        entity.setFeaturedHome(dto.featuredHome());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Promotion getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found: " + id));
    }

    private PromotionResponse toResponse(Promotion e) {
        return new PromotionResponse(e.getId(), e.getProduct().getId(), e.getPromotionalPrice(),
            e.getStartsAt(), e.getEndsAt(), e.getFeaturedHome());
    }
}
