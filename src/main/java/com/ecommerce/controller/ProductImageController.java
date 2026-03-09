package com.ecommerce.controller;

import com.ecommerce.dto.ProductImageRequest;
import com.ecommerce.dto.ProductImageResponse;
import com.ecommerce.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService service;

    @GetMapping
    public List<ProductImageResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductImageResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductImageResponse create(@RequestBody ProductImageRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ProductImageResponse update(@PathVariable Long id, @RequestBody ProductImageRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
