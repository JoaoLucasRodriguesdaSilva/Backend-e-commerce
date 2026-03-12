package com.ecommerce.controller;

import com.ecommerce.dto.AddressRequest;
import com.ecommerce.dto.AddressResponse;
import com.ecommerce.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Address")
@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService service;

    @GetMapping
    public List<AddressResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AddressResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponse create(@RequestBody AddressRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public AddressResponse update(@PathVariable Long id, @RequestBody AddressRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
