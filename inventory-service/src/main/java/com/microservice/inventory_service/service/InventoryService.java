package com.microservice.inventory_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.microservice.inventory_service.dto.InventoryResponse;
import com.microservice.inventory_service.repository.InventoryRepository;

import jakarta.transaction.Transactional;

@Service
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	public InventoryService(InventoryRepository inventoryRepository) {
		this.inventoryRepository = inventoryRepository;
	}

	@Transactional(rollbackOn = Exception.class)
	public List<InventoryResponse> InStock(List<String> skuCode) {
		List<InventoryResponse> iventoryIsInStock = inventoryRepository
				.findBySkuCodeIn(skuCode).stream().map(inventory -> InventoryResponse.builder()
						.skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantidade() > 0).build())
				.collect(Collectors.toList());
		
		return iventoryIsInStock;
	}

}
