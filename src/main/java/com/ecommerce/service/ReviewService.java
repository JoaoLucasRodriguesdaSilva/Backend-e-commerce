package com.ecommerce.service;

import com.ecommerce.dto.ReviewRequest;
import com.ecommerce.dto.ReviewResponse;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Review;
import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public List<ReviewResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ReviewResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public ReviewResponse create(ReviewRequest dto) {
        Product product = productRepository.findById(dto.productId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        Review entity = Review.builder()
            .product(product)
            .customer(customer)
            .rating(dto.rating())
            .title(dto.title())
            .comment(dto.comment())
            .verifiedPurchase(dto.verifiedPurchase())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public ReviewResponse update(Long id, ReviewRequest dto) {
        Review entity = getOrThrow(id);
        Product product = productRepository.findById(dto.productId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        entity.setProduct(product);
        entity.setCustomer(customer);
        entity.setRating(dto.rating());
        entity.setTitle(dto.title());
        entity.setComment(dto.comment());
        entity.setVerifiedPurchase(dto.verifiedPurchase());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Review getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found: " + id));
    }

    private ReviewResponse toResponse(Review e) {
        return new ReviewResponse(e.getId(), e.getProduct().getId(), e.getCustomer().getId(),
            e.getRating(), e.getTitle(), e.getComment(), e.getVerifiedPurchase(), e.getCreatedAt());
    }
}
