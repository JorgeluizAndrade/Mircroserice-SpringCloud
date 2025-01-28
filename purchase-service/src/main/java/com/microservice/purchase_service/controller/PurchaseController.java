package com.microservice.purchase_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.purchase_service.dto.PurchaseRequest;
import com.microservice.purchase_service.service.PurchaseService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

	
	private final PurchaseService purchaseService;
	
	public PurchaseController(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@CircuitBreaker(name = "inventory",  fallbackMethod = "fallbackMethod")
	public String dispatchOrder(@RequestBody PurchaseRequest req) {
		purchaseService.dispatchOrder(req);
		
		return "Order dispatched successful";
	}
	
	
	public String fallbackMethod(PurchaseRequest req, RuntimeException runtimeException) {
		return "Erro ao chamar inventário, favor efetuar compra mais tarde.";
		
	}
	
}
