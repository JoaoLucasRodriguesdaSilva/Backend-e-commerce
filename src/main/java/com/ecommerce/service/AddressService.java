package com.ecommerce.service;

import com.ecommerce.dto.AddressRequest;
import com.ecommerce.dto.AddressResponse;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.Customer;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;
    private final CustomerRepository customerRepository;

    public List<AddressResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public AddressResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public AddressResponse create(AddressRequest dto) {
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        Address entity = Address.builder()
            .customer(customer)
            .type(dto.type())
            .zipCode(dto.zipCode())
            .street(dto.street())
            .number(dto.number())
            .complement(dto.complement())
            .neighborhood(dto.neighborhood())
            .city(dto.city())
            .state(dto.state())
            .isDefault(dto.isDefault())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public AddressResponse update(Long id, AddressRequest dto) {
        Address entity = getOrThrow(id);
        Customer customer = customerRepository.findById(dto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        entity.setCustomer(customer);
        entity.setType(dto.type());
        entity.setZipCode(dto.zipCode());
        entity.setStreet(dto.street());
        entity.setNumber(dto.number());
        entity.setComplement(dto.complement());
        entity.setNeighborhood(dto.neighborhood());
        entity.setCity(dto.city());
        entity.setState(dto.state());
        entity.setIsDefault(dto.isDefault());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Address getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found: " + id));
    }

    private AddressResponse toResponse(Address e) {
        return new AddressResponse(e.getId(), e.getCustomer().getId(), e.getType(), e.getZipCode(),
            e.getStreet(), e.getNumber(), e.getComplement(), e.getNeighborhood(), e.getCity(),
            e.getState(), e.getIsDefault());
    }
}
