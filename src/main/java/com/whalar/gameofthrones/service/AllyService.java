package com.whalar.gameofthrones.service;

import com.whalar.gameofthrones.model.Ally;
import com.whalar.gameofthrones.repository.AllyRepository;
import com.whalar.gameofthrones.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AllyService {

	private final AllyRepository allyRepository;
	private final CharacterRepository characterRepository;

	public void create(long characterId, long allyId) {
		var character = characterRepository.findByIdOrThrowException(characterId);
		var allyCharacter = characterRepository.findByIdOrThrowException(allyId);
		var ally = new Ally();
		ally.setCharacter(character);
		ally.setAlly(allyCharacter);
		allyRepository.save(ally);
	}

	public void delete(long characterId, long allyId) {
		var ally = allyRepository.findByCharacterIdAndAllyId(characterId, allyId);
		allyRepository.delete(ally);
	}
}
