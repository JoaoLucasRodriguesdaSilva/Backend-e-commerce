package com.ecommerce.service;

import com.ecommerce.dto.PaymentRequest;
import com.ecommerce.dto.PaymentResponse;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Payment;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final OrderRepository orderRepository;

    public List<PaymentResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public PaymentResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public PaymentResponse create(PaymentRequest dto) {
        Order order = orderRepository.findById(dto.orderId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        Payment entity = Payment.builder()
            .order(order)
            .method(dto.method())
            .status(dto.status())
            .amount(dto.amount())
            .installments(dto.installments())
            .gateway(dto.gateway())
            .gatewayTransactionId(dto.gatewayTransactionId())
            .paidAt(dto.paidAt())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public PaymentResponse update(Long id, PaymentRequest dto) {
        Payment entity = getOrThrow(id);
        Order order = orderRepository.findById(dto.orderId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        entity.setOrder(order);
        entity.setMethod(dto.method());
        entity.setStatus(dto.status());
        entity.setAmount(dto.amount());
        entity.setInstallments(dto.installments());
        entity.setGateway(dto.gateway());
        entity.setGatewayTransactionId(dto.gatewayTransactionId());
        entity.setPaidAt(dto.paidAt());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Payment getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found: " + id));
    }

    private PaymentResponse toResponse(Payment e) {
        return new PaymentResponse(e.getId(), e.getOrder().getId(), e.getMethod(), e.getStatus(),
            e.getAmount(), e.getInstallments(), e.getGateway(), e.getGatewayTransactionId(), e.getPaidAt());
    }
}
