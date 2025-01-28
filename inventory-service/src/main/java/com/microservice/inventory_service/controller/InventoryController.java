package com.microservice.inventory_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.inventory_service.dto.InventoryResponse;
import com.microservice.inventory_service.service.InventoryService;

@RestController
@RequestMapping("/api")
public class InventoryController {

	private final InventoryService inventoryService;

	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@GetMapping("/inventory")
	public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
		return inventoryService.InStock(skuCode);
	}

}
