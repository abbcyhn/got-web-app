package com.whalar.gameofthrones.rabbitmq;

import com.whalar.gameofthrones.config.RabbitMQConfig;
import com.whalar.gameofthrones.mapper.CharacterMapper;
import com.whalar.gameofthrones.repository.CharacterDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class RabbitMQConsumer {

	private final CharacterMapper characterMapper;
	private final CharacterDocumentRepository characterDocumentRepository;

	@RabbitListener(queues = RabbitMQConfig.CHARACTER_QUEUE)
	public void receive(CharacterPayloadDto characterPayloadDto) {
		try {
			characterDocumentRepository.save(characterMapper.mapToCharacterDocument(characterPayloadDto));
		} catch (Exception ex) {
			log.error("Error ocquired in consumer. ", ex);
		}
	}
}

