package com.microservice.purchase_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservice.purchase_service.dto.InventoryResponse;
import com.microservice.purchase_service.dto.ItensPurchaseDto;
import com.microservice.purchase_service.dto.NotificationRequest;
import com.microservice.purchase_service.dto.PurchaseRequest;
import com.microservice.purchase_service.entity.ItensPurchase;
import com.microservice.purchase_service.entity.Purchase;
import com.microservice.purchase_service.repository.PurchaseRepository;

import jakarta.ws.rs.core.UriBuilder;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PurchaseService {

	private final PurchaseRepository puchaseRepository;

	private final WebClient.Builder webClientBuilder;

	private final RabbitTemplate rabbitTemplate;

	public PurchaseService(PurchaseRepository puchaseRepository, WebClient.Builder webClientBuilder,
			RabbitTemplate rabbitTemplate) {
		this.puchaseRepository = puchaseRepository;
		this.webClientBuilder = webClientBuilder;
		this.rabbitTemplate = rabbitTemplate;
	}

	public void dispatchOrder(PurchaseRequest req) {
		Purchase purchase = new Purchase();

		purchase.setNumPurchase(UUID.randomUUID().toString());

		List<ItensPurchase> itensPurchase = req.getItensPurchaseDto().stream().map(this::mapToDto)
				.collect(Collectors.toList());

		purchase.setItensPurchase(itensPurchase);

		List<String> skuCodes = purchase.getItensPurchase().stream().map(ItensPurchase::getSkuCode)
				.collect(Collectors.toList());

		log.info("SkuCodes do Pedido: " + skuCodes);

		InventoryResponse[] inventoryResponse = webClientBuilder.build().get()
				.uri("http://inventory-service/api/inventory",
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes.toArray()).build())
				.retrieve().bodyToMono(InventoryResponse[].class)
				.doOnError(error -> log.error("Erro ao chamar inventário: {}", error.getMessage())).block();

		log.info("Resposta do serviço de inventário: {}", Arrays.toString(inventoryResponse));

		boolean allInStock = Arrays.stream(inventoryResponse).peek(resp -> log.info("Resposta {} ", resp))
				.allMatch(InventoryResponse::isInStock);
		log.info("Todos os produtos existem no estoque: " + allInStock);

		if (allInStock) {
			puchaseRepository.save(purchase);
		} else {
			throw new IllegalArgumentException("Produto fora do estoque. Realizar Tentativa mais tarde.");
		}

	}

	private ItensPurchase mapToDto(ItensPurchaseDto itensPurchaseDto) {

		ItensPurchase itensPurchase = new ItensPurchase();

		itensPurchase.setPrice(itensPurchaseDto.getPrice());
		itensPurchase.setQuantidade(itensPurchaseDto.getQuantidade());
		itensPurchase.setSkuCode(itensPurchaseDto.getSkuCode());

		return itensPurchase;
	}

	public void sendPurchaseNotification(String email, String message) {
		NotificationRequest req = new NotificationRequest(email, message);
		rabbitTemplate.convertAndSend("purchase.exchange", "purchase.routing.key", req);
	}

}
