package com.ecommerce.service;

import com.ecommerce.dto.OrderReturnRequest;
import com.ecommerce.dto.OrderReturnResponse;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderReturn;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.OrderReturnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderReturnService {

    private final OrderReturnRepository repository;
    private final OrderRepository orderRepository;

    public List<OrderReturnResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public OrderReturnResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public OrderReturnResponse create(OrderReturnRequest dto) {
        Order order = orderRepository.findById(dto.orderId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        OrderReturn entity = OrderReturn.builder()
            .order(order)
            .reason(dto.reason())
            .status(dto.status())
            .type(dto.type())
            .approvedAt(dto.approvedAt())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public OrderReturnResponse update(Long id, OrderReturnRequest dto) {
        OrderReturn entity = getOrThrow(id);
        Order order = orderRepository.findById(dto.orderId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        entity.setOrder(order);
        entity.setReason(dto.reason());
        entity.setStatus(dto.status());
        entity.setType(dto.type());
        entity.setApprovedAt(dto.approvedAt());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private OrderReturn getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "OrderReturn not found: " + id));
    }

    private OrderReturnResponse toResponse(OrderReturn e) {
        return new OrderReturnResponse(e.getId(), e.getOrder().getId(), e.getReason(),
            e.getStatus(), e.getType(), e.getApprovedAt());
    }
}
