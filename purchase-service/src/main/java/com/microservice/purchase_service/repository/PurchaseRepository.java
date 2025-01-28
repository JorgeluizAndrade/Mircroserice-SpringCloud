package com.microservice.purchase_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.purchase_service.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

}
