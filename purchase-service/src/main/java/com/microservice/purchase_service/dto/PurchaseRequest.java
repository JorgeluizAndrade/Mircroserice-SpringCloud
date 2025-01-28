package com.microservice.purchase_service.dto;

import java.util.List;

import com.microservice.purchase_service.entity.ItensPurchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {
	private List<ItensPurchaseDto> itensPurchaseDto;
}
