package com.ecommerce.controller;

import com.ecommerce.dto.TechnicalSpecificationRequest;
import com.ecommerce.dto.TechnicalSpecificationResponse;
import com.ecommerce.service.TechnicalSpecificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "TechnicalSpecification")
@RestController
@RequestMapping("/api/technical-specifications")
@RequiredArgsConstructor
public class TechnicalSpecificationController {

    private final TechnicalSpecificationService service;

    @GetMapping
    public List<TechnicalSpecificationResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TechnicalSpecificationResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TechnicalSpecificationResponse create(@RequestBody TechnicalSpecificationRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public TechnicalSpecificationResponse update(@PathVariable Long id, @RequestBody TechnicalSpecificationRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
