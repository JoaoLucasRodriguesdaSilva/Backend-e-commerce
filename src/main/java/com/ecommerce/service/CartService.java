package com.ecommerce.service;

import com.ecommerce.dto.CartRequest;
import com.ecommerce.dto.CartResponse;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Customer;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository repository;
    private final CustomerRepository customerRepository;

    public List<CartResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public CartResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public CartResponse create(CartRequest dto) {
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        Cart entity = Cart.builder()
            .customer(customer)
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public CartResponse update(Long id, CartRequest dto) {
        Cart entity = getOrThrow(id);
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        entity.setCustomer(customer);
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Cart getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found: " + id));
    }

    private CartResponse toResponse(Cart e) {
        return new CartResponse(e.getId(), e.getCustomer().getId(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
