package com.whalar.gameofthrones.service;

import com.whalar.gameofthrones.contract.CharacterSaveRequest;
import com.whalar.gameofthrones.contract.CharacterSearchResponseDto;
import com.whalar.gameofthrones.contract.CharacterSearchRequest;
import com.whalar.gameofthrones.mapper.ActionMapper;
import com.whalar.gameofthrones.mapper.AllyMapper;
import com.whalar.gameofthrones.mapper.CharacterMapper;
import com.whalar.gameofthrones.mapper.HouseMapper;
import com.whalar.gameofthrones.mapper.RelationshipMapper;
import com.whalar.gameofthrones.rabbitmq.RabbitMQProducer;
import com.whalar.gameofthrones.model.Character;
import com.whalar.gameofthrones.repository.ActionRepository;
import com.whalar.gameofthrones.repository.ActorRepository;
import com.whalar.gameofthrones.repository.AllyRepository;
import com.whalar.gameofthrones.repository.CharacterDocumentRepository;
import com.whalar.gameofthrones.repository.CharacterRepository;
import com.whalar.gameofthrones.repository.HouseRepository;
import com.whalar.gameofthrones.repository.RelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class CharacterService {

	private final AllyMapper allyMapper;
	private final HouseMapper houseMapper;
	private final ActionMapper actionMapper;
	private final CharacterMapper characterMapper;
	private final RelationshipMapper relationshipMapper;

	private final AllyRepository allyRepository;
	private final ActorRepository actorRepository;
	private final HouseRepository houseRepository;
	private final ActionRepository actionRepository;
	private final CharacterRepository characterRepository;
	private final RelationshipRepository relationshipRepository;

	private final CharacterDocumentRepository characterDocumentRepository;

	private final RabbitMQProducer rabbitMQProducer;

	public List<CharacterSearchResponseDto> getAll(CharacterSearchRequest request) {
		var characters = characterDocumentRepository.findAllByRequest(request);
		return characterMapper.mapToCharacterSearchDtoList(characters);
	}

	public CharacterSearchResponseDto getById(long characterId) {
		var character = characterRepository.findByIdOrThrowException(characterId);
		return characterMapper.mapToCharacterSearchDto(character);
	}

	public URI create(CharacterSaveRequest request) {
		var character = save(request, null);
		rabbitMQProducer.send(characterMapper.mapToCharacterPayloadDto(character));
		return ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(character.getId())
			.toUri();
	}

	public URI update(long characterId, CharacterSaveRequest request) {
		var character = save(request, characterId);
		rabbitMQProducer.send(characterMapper.mapToCharacterPayloadDto(character));
		return ServletUriComponentsBuilder
			.fromCurrentRequest()
			.buildAndExpand(characterId)
			.toUri();
	}

	public void delete(long characterId) {
		deleteDetails(characterId);
		characterDocumentRepository.deleteById(Long.toString(characterId));
	}

	private Character save(CharacterSaveRequest request, Long characterId) {
		var character = characterMapper.mapToCharacterSearchDto(request);
		if (characterId != null) {
			character.setId(characterId);
			deleteDetails(characterId);
		}

		character = characterRepository.save(character);
		allyMapper.mapToAllies(character, request).forEach(allyRepository::save);
		houseMapper.mapToHouses(character, request).forEach(houseRepository::save);
		actionMapper.mapToActions(character, request).forEach(actionRepository::save);
		relationshipMapper.mapToRelationships(character, request).forEach(relationshipRepository::save);
		actorRepository.addAllActorsToCharacter(character, request.getActors());

		return character;
	}

	private void deleteDetails(long characterId) {
		allyRepository
			.findAll()
			.stream()
			.filter(x -> x.getCharacter() != null && x.getCharacter().getId() == characterId)
			.forEach(allyRepository::delete);
		houseRepository
			.findAll()
			.stream()
			.filter(x -> x.getCharacter() != null && x.getCharacter().getId() == characterId)
			.forEach(houseRepository::delete);
		actionRepository
			.findAll()
			.stream()
			.filter(x -> x.getCharacter() != null && x.getCharacter().getId() == characterId)
			.forEach(actionRepository::delete);
		relationshipRepository
			.findAll()
			.stream()
			.filter(x -> x.getCharacter() != null && x.getCharacter().getId() == characterId)
			.forEach(relationshipRepository::delete);
		actorRepository.deleteAllActorsFromCharacter(characterId);
	}
}