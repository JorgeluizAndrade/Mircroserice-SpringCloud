package com.microservice.product_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.product_service.dto.ProductRequestDTO;
import com.microservice.product_service.dto.ProductResponseDTO;
import com.microservice.product_service.entity.Product;
import com.microservice.product_service.mapper.ProductMapper;
import com.microservice.product_service.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/products")
public class ProductController {

	private final ProductService productService;

	private final ProductMapper mapper;

	public ProductController(ProductService productService, ProductMapper mapper) {
		this.productService = productService;
		this.mapper = mapper;
	}

	@PostMapping
	public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO prud) {

		Product newProduct = mapper.toEntity(prud);

		var product = productService.createProduct(newProduct);

		ProductResponseDTO response = mapper.toResponseDTO(product);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	
	@GetMapping
	public ResponseEntity findAllProducts() {
		var allProducts = productService.allProducts();

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(allProducts);
	}
}
