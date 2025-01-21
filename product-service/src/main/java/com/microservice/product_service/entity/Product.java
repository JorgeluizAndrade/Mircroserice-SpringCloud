package com.microservice.product_service.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Data
@Getter
@Setter
@NoArgsConstructor
public class Product {

	@Id
	private String id;

	private String name;

	private String description; 

	private BigDecimal price;
	
	public Product(String name, String description, BigDecimal price ) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	
}
