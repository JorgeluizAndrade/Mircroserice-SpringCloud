package com.microservice.product_service.mapper;

import org.springframework.stereotype.Component;

import com.microservice.product_service.dto.ProductRequestDTO;
import com.microservice.product_service.dto.ProductResponseDTO;
import com.microservice.product_service.entity.Product;

@Component
public class ProductMapper {

	public Product toEntity(ProductRequestDTO dto) {

		Product product = new Product();

		product.setName(dto.name());
		product.setDescription(dto.description());
		product.setPrice(dto.price());

		return product;
	}

	public ProductResponseDTO toResponseDTO(Product prod) {
		return new ProductResponseDTO(prod.getId(), prod.getName(), prod.getDescription(), prod.getPrice());
	}
}
