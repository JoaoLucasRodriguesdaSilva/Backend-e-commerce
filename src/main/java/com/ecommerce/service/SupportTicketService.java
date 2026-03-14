package com.ecommerce.service;

import com.ecommerce.dto.SupportTicketRequest;
import com.ecommerce.dto.SupportTicketResponse;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.SupportTicket;
import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.SupportTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportTicketService {

    private final SupportTicketRepository repository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public List<SupportTicketResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public SupportTicketResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public SupportTicketResponse create(SupportTicketRequest dto) {
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        Order order = dto.orderId() != null
            ? orderRepository.findById(dto.orderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"))
            : null;
        SupportTicket entity = SupportTicket.builder()
            .customer(customer)
            .order(order)
            .subject(dto.subject())
            .status(dto.status())
            .channel(dto.channel())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public SupportTicketResponse update(Long id, SupportTicketRequest dto) {
        SupportTicket entity = getOrThrow(id);
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        Order order = dto.orderId() != null
            ? orderRepository.findById(dto.orderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"))
            : null;
        entity.setCustomer(customer);
        entity.setOrder(order);
        entity.setSubject(dto.subject());
        entity.setStatus(dto.status());
        entity.setChannel(dto.channel());
        return toResponse(repository.save(entity));
    }

    public List<SupportTicketResponse> findAllByEmail(String email) {
        return repository.findByCustomer_Email(email).stream().map(this::toResponse).toList();
    }

    public SupportTicketResponse findByIdAndEmail(Long id, String email) {
        return toResponse(repository.findByIdAndCustomer_Email(id, email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "SupportTicket not found: " + id)));
    }

    @Transactional
    public SupportTicketResponse createForUser(SupportTicketRequest dto, String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer not found for current user"));
        Order order = dto.orderId() != null
            ? orderRepository.findByIdAndCustomer_Email(dto.orderId(), email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "Order not found or does not belong to the current user"))
            : null;
        SupportTicket entity = SupportTicket.builder()
            .customer(customer)
            .order(order)
            .subject(dto.subject())
            .status(dto.status())
            .channel(dto.channel())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void deleteByIdAndEmail(Long id, String email) {
        repository.findByIdAndCustomer_Email(id, email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "SupportTicket not found: " + id));
        repository.deleteById(id);
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private SupportTicket getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SupportTicket not found: " + id));
    }

    private SupportTicketResponse toResponse(SupportTicket e) {
        return new SupportTicketResponse(e.getId(), e.getCustomer().getId(),
            e.getOrder() != null ? e.getOrder().getId() : null,
            e.getSubject(), e.getStatus(), e.getChannel(), e.getCreatedAt());
    }
}
