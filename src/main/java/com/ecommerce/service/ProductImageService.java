package com.ecommerce.service;

import com.ecommerce.dto.ProductImageRequest;
import com.ecommerce.dto.ProductImageResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductImage;
import com.ecommerce.repository.ProductImageRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository repository;
    private final ProductRepository productRepository;

    public List<ProductImageResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ProductImageResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public ProductImageResponse create(ProductImageRequest dto) {
        Product product = productRepository.findById(dto.productId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        ProductImage entity = ProductImage.builder()
            .product(product)
            .url(dto.url())
            .alt(dto.alt())
            .displayOrder(dto.displayOrder())
            .isPrimary(dto.isPrimary())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public ProductImageResponse update(Long id, ProductImageRequest dto) {
        ProductImage entity = getOrThrow(id);
        Product product = productRepository.findById(dto.productId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        entity.setProduct(product);
        entity.setUrl(dto.url());
        entity.setAlt(dto.alt());
        entity.setDisplayOrder(dto.displayOrder());
        entity.setIsPrimary(dto.isPrimary());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private ProductImage getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ProductImage not found: " + id));
    }

    private ProductImageResponse toResponse(ProductImage e) {
        return new ProductImageResponse(e.getId(), e.getProduct().getId(), e.getUrl(), e.getAlt(),
            e.getDisplayOrder(), e.getIsPrimary());
    }
}
