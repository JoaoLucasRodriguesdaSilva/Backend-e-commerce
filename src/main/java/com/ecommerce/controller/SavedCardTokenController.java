package com.ecommerce.controller;

import com.ecommerce.dto.SavedCardTokenRequest;
import com.ecommerce.dto.SavedCardTokenResponse;
import com.ecommerce.service.SavedCardTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-card-tokens")
@RequiredArgsConstructor
public class SavedCardTokenController {

    private final SavedCardTokenService service;

    @GetMapping
    public List<SavedCardTokenResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SavedCardTokenResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SavedCardTokenResponse create(@RequestBody SavedCardTokenRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public SavedCardTokenResponse update(@PathVariable Long id, @RequestBody SavedCardTokenRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
