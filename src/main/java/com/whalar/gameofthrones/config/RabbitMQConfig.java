package com.whalar.gameofthrones.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

	public static final String CHARACTER_QUEUE = "character-queue";
	public static final String CHARACTER_EXCHANGE = "character-exchange";
	public static final String CHARACTER_ROUTING = "character-routing";

	@Bean
	public Jackson2JsonMessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Queue queue() {
		return new Queue(CHARACTER_QUEUE, true);
	}

	@Bean
	public DirectExchange exchange() {
		return new DirectExchange(CHARACTER_EXCHANGE);
	}

	@Bean
	public Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(CHARACTER_ROUTING);
	}
}
