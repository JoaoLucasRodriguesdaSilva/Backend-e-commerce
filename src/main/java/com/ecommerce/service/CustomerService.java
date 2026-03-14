package com.ecommerce.service;

import com.ecommerce.dto.CustomerRequest;
import com.ecommerce.dto.CustomerResponse;
import com.ecommerce.entity.Customer;
import com.ecommerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public List<CustomerResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public CustomerResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public CustomerResponse create(CustomerRequest dto) {
        Customer entity = Customer.builder()
            .name(dto.name())
            .email(dto.email())
            .taxId(dto.taxId())
            .phone(dto.phone())
            .birthDate(dto.birthDate())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public CustomerResponse update(Long id, CustomerRequest dto) {
        Customer entity = getOrThrow(id);
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setTaxId(dto.taxId());
        entity.setPhone(dto.phone());
        entity.setBirthDate(dto.birthDate());
        return toResponse(repository.save(entity));
    }

    public CustomerResponse findByEmail(String email) {
        return toResponse(repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer not found for current user")));
    }

    @Transactional
    public CustomerResponse createForUser(CustomerRequest dto, String email) {
        Customer entity = Customer.builder()
            .name(dto.name())
            .email(email)
            .taxId(dto.taxId())
            .phone(dto.phone())
            .birthDate(dto.birthDate())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public CustomerResponse updateByEmail(String email, CustomerRequest dto) {
        Customer entity = repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer not found for current user"));
        entity.setName(dto.name());
        entity.setTaxId(dto.taxId());
        entity.setPhone(dto.phone());
        entity.setBirthDate(dto.birthDate());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Customer getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found: " + id));
    }

    private CustomerResponse toResponse(Customer e) {
        return new CustomerResponse(e.getId(), e.getName(), e.getEmail(), e.getTaxId(),
            e.getPhone(), e.getBirthDate(), e.getCreatedAt());
    }
}
