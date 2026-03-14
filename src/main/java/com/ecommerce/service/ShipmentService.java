package com.ecommerce.service;

import com.ecommerce.dto.ShipmentRequest;
import com.ecommerce.dto.ShipmentResponse;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Shipment;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository repository;
    private final OrderRepository orderRepository;

    public List<ShipmentResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ShipmentResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public ShipmentResponse create(ShipmentRequest dto) {
        Order order = orderRepository.findById(dto.orderId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        Shipment entity = Shipment.builder()
            .order(order)
            .carrier(dto.carrier())
            .service(dto.service())
            .trackingCode(dto.trackingCode())
            .status(dto.status())
            .cost(dto.cost())
            .estimatedDays(dto.estimatedDays())
            .shippedAt(dto.shippedAt())
            .deliveredAt(dto.deliveredAt())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public ShipmentResponse update(Long id, ShipmentRequest dto) {
        Shipment entity = getOrThrow(id);
        Order order = orderRepository.findById(dto.orderId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        entity.setOrder(order);
        entity.setCarrier(dto.carrier());
        entity.setService(dto.service());
        entity.setTrackingCode(dto.trackingCode());
        entity.setStatus(dto.status());
        entity.setCost(dto.cost());
        entity.setEstimatedDays(dto.estimatedDays());
        entity.setShippedAt(dto.shippedAt());
        entity.setDeliveredAt(dto.deliveredAt());
        return toResponse(repository.save(entity));
    }

    public List<ShipmentResponse> findAllByEmail(String email) {
        return repository.findByOrder_Customer_Email(email).stream().map(this::toResponse).toList();
    }

    public ShipmentResponse findByIdAndEmail(Long id, String email) {
        return toResponse(repository.findByIdAndOrder_Customer_Email(id, email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found: " + id)));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Shipment getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found: " + id));
    }

    private ShipmentResponse toResponse(Shipment e) {
        return new ShipmentResponse(e.getId(), e.getOrder().getId(), e.getCarrier(), e.getService(),
            e.getTrackingCode(), e.getStatus(), e.getCost(), e.getEstimatedDays(),
            e.getShippedAt(), e.getDeliveredAt());
    }
}
