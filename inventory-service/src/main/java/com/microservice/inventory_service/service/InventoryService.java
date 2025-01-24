package com.microservice.inventory_service.service;

import org.springframework.stereotype.Service;

import com.microservice.inventory_service.repository.InventoryRepository;

import jakarta.transaction.Transactional;

@Service
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	public InventoryService(InventoryRepository inventoryRepository) {
		this.inventoryRepository = inventoryRepository;
	}

	@Transactional(rollbackOn = Exception.class)
	public boolean isInStock(String skuCode) {
		return inventoryRepository.findBySkuCode(skuCode).isPresent();
	}

}
