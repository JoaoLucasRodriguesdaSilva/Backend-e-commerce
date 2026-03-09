package com.ecommerce.service;

import com.ecommerce.dto.SessionRequest;
import com.ecommerce.dto.SessionResponse;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Session;
import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository repository;
    private final CustomerRepository customerRepository;

    public List<SessionResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public SessionResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public SessionResponse create(SessionRequest dto) {
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        Session entity = Session.builder()
            .customer(customer)
            .token(dto.token())
            .expiresAt(dto.expiresAt())
            .ip(dto.ip())
            .userAgent(dto.userAgent())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public SessionResponse update(Long id, SessionRequest dto) {
        Session entity = getOrThrow(id);
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        entity.setCustomer(customer);
        entity.setToken(dto.token());
        entity.setExpiresAt(dto.expiresAt());
        entity.setIp(dto.ip());
        entity.setUserAgent(dto.userAgent());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Session getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found: " + id));
    }

    private SessionResponse toResponse(Session e) {
        return new SessionResponse(e.getId(), e.getCustomer().getId(), e.getToken(),
            e.getExpiresAt(), e.getIp(), e.getUserAgent());
    }
}
