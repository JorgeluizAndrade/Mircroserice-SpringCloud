package com.microservice.product_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.microservice.product_service.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
