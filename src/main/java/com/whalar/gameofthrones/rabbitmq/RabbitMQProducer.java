package com.whalar.gameofthrones.rabbitmq;

import com.whalar.gameofthrones.config.RabbitMQConfig;
import io.github.springwolf.bindings.amqp.annotations.AmqpAsyncOperationBinding;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import io.github.springwolf.core.asyncapi.annotations.AsyncPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQProducer {

	private final RabbitTemplate rabbitTemplate;

	@AsyncPublisher(
		operation =
		@AsyncOperation(
			channelName = RabbitMQConfig.CHARACTER_EXCHANGE,
			description = "Sends the created character to the " + RabbitMQConfig.CHARACTER_EXCHANGE))
	@AmqpAsyncOperationBinding()
	public void send(CharacterPayloadDto characterPayload) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.CHARACTER_EXCHANGE,
			RabbitMQConfig.CHARACTER_ROUTING,
			characterPayload
		);
	}
}

