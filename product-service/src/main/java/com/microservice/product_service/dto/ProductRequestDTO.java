package com.microservice.product_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

public record ProductRequestDTO( String name, String description, BigDecimal price) {

}
