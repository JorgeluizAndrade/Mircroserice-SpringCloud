package com.microservice.product_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.microservice.product_service.entity.Product;
import com.microservice.product_service.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Product createProduct(Product productDTO) {
		Product product = new Product(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice());

		productRepository.save(product);

		log.info("Produto salvo Id:{}, Nome:{}, Preço:{}, Descrição:{}", product.getId(), product.getName(),
				product.getPrice(), product.getDescription());

		return product;

	}
	
	public List<Product> allProducts() {
			return this.productRepository.findAll();
		}

}
