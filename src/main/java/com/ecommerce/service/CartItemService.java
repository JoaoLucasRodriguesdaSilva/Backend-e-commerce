package com.ecommerce.service;

import com.ecommerce.dto.CartItemRequest;
import com.ecommerce.dto.CartItemResponse;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Variant;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository repository;
    private final CartRepository cartRepository;
    private final VariantRepository variantRepository;

    public List<CartItemResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public CartItemResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public CartItemResponse create(CartItemRequest dto) {
        Cart cart = cartRepository.findById(dto.cartId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        Variant variant = variantRepository.findById(dto.variantId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found"));
        CartItem entity = CartItem.builder()
            .cart(cart)
            .variant(variant)
            .quantity(dto.quantity())
            .unitPrice(dto.unitPrice())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public CartItemResponse update(Long id, CartItemRequest dto) {
        CartItem entity = getOrThrow(id);
        Cart cart = cartRepository.findById(dto.cartId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        Variant variant = variantRepository.findById(dto.variantId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found"));
        entity.setCart(cart);
        entity.setVariant(variant);
        entity.setQuantity(dto.quantity());
        entity.setUnitPrice(dto.unitPrice());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private CartItem getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CartItem not found: " + id));
    }

    private CartItemResponse toResponse(CartItem e) {
        return new CartItemResponse(e.getId(), e.getCart().getId(), e.getVariant().getId(),
            e.getQuantity(), e.getUnitPrice());
    }
}
