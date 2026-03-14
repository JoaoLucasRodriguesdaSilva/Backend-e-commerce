package com.ecommerce.service;

import com.ecommerce.dto.OrderItemRequest;
import com.ecommerce.dto.OrderItemResponse;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Variant;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository repository;
    private final OrderRepository orderRepository;
    private final VariantRepository variantRepository;

    public List<OrderItemResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public OrderItemResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public OrderItemResponse create(OrderItemRequest dto) {
        Order order = orderRepository.findById(dto.orderId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        Variant variant = variantRepository.findById(dto.variantId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found"));
        OrderItem entity = OrderItem.builder()
            .order(order)
            .variant(variant)
            .quantity(dto.quantity())
            .unitPrice(dto.unitPrice())
            .nameSnapshot(dto.nameSnapshot())
            .skuSnapshot(dto.skuSnapshot())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public OrderItemResponse update(Long id, OrderItemRequest dto) {
        OrderItem entity = getOrThrow(id);
        Order order = orderRepository.findById(dto.orderId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        Variant variant = variantRepository.findById(dto.variantId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found"));
        entity.setOrder(order);
        entity.setVariant(variant);
        entity.setQuantity(dto.quantity());
        entity.setUnitPrice(dto.unitPrice());
        entity.setNameSnapshot(dto.nameSnapshot());
        entity.setSkuSnapshot(dto.skuSnapshot());
        return toResponse(repository.save(entity));
    }

    public List<OrderItemResponse> findAllByEmail(String email) {
        return repository.findByOrder_Customer_Email(email).stream().map(this::toResponse).toList();
    }

    public OrderItemResponse findByIdAndEmail(Long id, String email) {
        return toResponse(repository.findByIdAndOrder_Customer_Email(id, email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "OrderItem not found: " + id)));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private OrderItem getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "OrderItem not found: " + id));
    }

    private OrderItemResponse toResponse(OrderItem e) {
        return new OrderItemResponse(e.getId(), e.getOrder().getId(), e.getVariant().getId(),
            e.getQuantity(), e.getUnitPrice(), e.getNameSnapshot(), e.getSkuSnapshot());
    }
}
