package com.ecommerce.service;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Order;
import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerRepository customerRepository;

    public List<OrderResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public OrderResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public OrderResponse create(OrderRequest dto) {
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        Order entity = Order.builder()
            .customer(customer)
            .status(dto.status())
            .subtotal(dto.subtotal())
            .discount(dto.discount())
            .shipping(dto.shipping())
            .total(dto.total())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public OrderResponse update(Long id, OrderRequest dto) {
        Order entity = getOrThrow(id);
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        entity.setCustomer(customer);
        entity.setStatus(dto.status());
        entity.setSubtotal(dto.subtotal());
        entity.setDiscount(dto.discount());
        entity.setShipping(dto.shipping());
        entity.setTotal(dto.total());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Order getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found: " + id));
    }

    private OrderResponse toResponse(Order e) {
        return new OrderResponse(e.getId(), e.getCustomer().getId(), e.getStatus(), e.getSubtotal(),
            e.getDiscount(), e.getShipping(), e.getTotal(), e.getCreatedAt());
    }
}
