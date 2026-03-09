package com.ecommerce.dto;

import com.ecommerce.enums.AddressType;

public record AddressRequest(
    Long customerId,
    AddressType type,
    String zipCode,
    String street,
    String number,
    String complement,
    String neighborhood,
    String city,
    String state,
    Boolean isDefault
) {}
