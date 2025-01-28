package com.microservice.purchase_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItensPurchaseDto {

	private Long id;
	private String skuCode;
	private BigDecimal price;
	private Integer quantidade;

}
