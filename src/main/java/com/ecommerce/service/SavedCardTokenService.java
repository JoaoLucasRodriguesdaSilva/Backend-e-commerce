package com.ecommerce.service;

import com.ecommerce.dto.SavedCardTokenRequest;
import com.ecommerce.dto.SavedCardTokenResponse;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.SavedCardToken;
import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.repository.SavedCardTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedCardTokenService {

    private final SavedCardTokenRepository repository;
    private final CustomerRepository customerRepository;

    public List<SavedCardTokenResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public SavedCardTokenResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public SavedCardTokenResponse create(SavedCardTokenRequest dto) {
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        SavedCardToken entity = SavedCardToken.builder()
            .customer(customer)
            .last4Digits(dto.last4Digits())
            .brand(dto.brand())
            .gatewayToken(dto.gatewayToken())
            .expiration(dto.expiration())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public SavedCardTokenResponse update(Long id, SavedCardTokenRequest dto) {
        SavedCardToken entity = getOrThrow(id);
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        entity.setCustomer(customer);
        entity.setLast4Digits(dto.last4Digits());
        entity.setBrand(dto.brand());
        entity.setGatewayToken(dto.gatewayToken());
        entity.setExpiration(dto.expiration());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private SavedCardToken getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SavedCardToken not found: " + id));
    }

    private SavedCardTokenResponse toResponse(SavedCardToken e) {
        return new SavedCardTokenResponse(e.getId(), e.getCustomer().getId(), e.getLast4Digits(),
            e.getBrand(), e.getGatewayToken(), e.getExpiration());
    }
}
