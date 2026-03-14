package com.ecommerce.controller;

import com.ecommerce.dto.ReviewRequest;
import com.ecommerce.dto.ReviewResponse;
import com.ecommerce.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Review")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PostMapping("/my")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse createMy(@RequestBody ReviewRequest dto,
                                   @AuthenticationPrincipal UserDetails user) {
        return service.createForUser(dto, user.getUsername());
    }

    @PutMapping("/my/{id}")
    public ReviewResponse updateMy(@PathVariable Long id, @RequestBody ReviewRequest dto,
                                   @AuthenticationPrincipal UserDetails user) {
        return service.updateByIdAndEmail(id, dto, user.getUsername());
    }

    @GetMapping
    public List<ReviewResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ReviewResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse create(@RequestBody ReviewRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ReviewResponse update(@PathVariable Long id, @RequestBody ReviewRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
