package com.ecommerce.controller;

import com.ecommerce.dto.SessionRequest;
import com.ecommerce.dto.SessionResponse;
import com.ecommerce.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService service;

    @GetMapping
    public List<SessionResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SessionResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse create(@RequestBody SessionRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public SessionResponse update(@PathVariable Long id, @RequestBody SessionRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
