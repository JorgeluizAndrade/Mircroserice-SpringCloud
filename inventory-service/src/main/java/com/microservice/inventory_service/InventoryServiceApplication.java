package com.microservice.inventory_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.microservice.inventory_service.entity.Inventory;
import com.microservice.inventory_service.repository.InventoryRepository;



@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner carregarDados(InventoryRepository inventoryRepository) {
			
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iphone_13");
			inventory.setQuantidade(26);
			
			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("iphone_14_red");
			inventory1.setQuantidade(0);
			
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}
}
