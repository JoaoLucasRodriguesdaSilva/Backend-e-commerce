package com.ecommerce.controller;

import com.ecommerce.dto.CartRequest;
import com.ecommerce.dto.CartResponse;
import com.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cart")
@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @GetMapping
    public List<CartResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CartResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse create(@RequestBody CartRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public CartResponse update(@PathVariable Long id, @RequestBody CartRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
