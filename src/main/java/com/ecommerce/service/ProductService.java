package com.ecommerce.service;

import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ProductResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest dto) {
        Product entity = Product.builder()
            .name(dto.name())
            .description(dto.description())
            .technicalDescription(dto.technicalDescription())
            .basePrice(dto.basePrice())
            .promotionalPrice(dto.promotionalPrice())
            .sku(dto.sku())
            .brand(dto.brand())
            .model(dto.model())
            .warrantyMonths(dto.warrantyMonths())
            .weight(dto.weight())
            .dimensions(dto.dimensions())
            .active(dto.active() != null ? dto.active() : true)
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest dto) {
        Product entity = getOrThrow(id);
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setTechnicalDescription(dto.technicalDescription());
        entity.setBasePrice(dto.basePrice());
        entity.setPromotionalPrice(dto.promotionalPrice());
        entity.setSku(dto.sku());
        entity.setBrand(dto.brand());
        entity.setModel(dto.model());
        entity.setWarrantyMonths(dto.warrantyMonths());
        entity.setWeight(dto.weight());
        entity.setDimensions(dto.dimensions());
        entity.setActive(dto.active());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Product getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id));
    }

    private ProductResponse toResponse(Product e) {
        return new ProductResponse(e.getId(), e.getName(), e.getDescription(), e.getTechnicalDescription(),
            e.getBasePrice(), e.getPromotionalPrice(), e.getSku(), e.getBrand(), e.getModel(),
            e.getWarrantyMonths(), e.getWeight(), e.getDimensions(), e.getActive(), e.getCreatedAt());
    }
}
