package com.microservice.notification_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMQConfig {

	@Bean
	public Queue queue() {
		return new Queue("purchase.queue", true);
	}

	@Bean
	public Exchange exchange() {
		return new DirectExchange("purchase.exchage");
	}

	@Bean
	public Binding binding(Queue purchaseQueue, Exchange directExchanse) {
		return BindingBuilder.bind(purchaseQueue).to(directExchanse).with("purchase.routing.key").noargs();
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		ObjectMapper objMapper = new ObjectMapper();

		return new Jackson2JsonMessageConverter(objMapper);

	}
}
