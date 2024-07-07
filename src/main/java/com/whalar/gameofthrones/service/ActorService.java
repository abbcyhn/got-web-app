package com.whalar.gameofthrones.service;

import com.whalar.gameofthrones.repository.ActorRepository;
import com.whalar.gameofthrones.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActorService {

	private final ActorRepository actorRepository;
	private final CharacterRepository characterRepository;

	public void addActorToCharacter(long characterId, long actorId) {
		var character = characterRepository.findByIdOrThrowException(characterId);
		var actor = actorRepository.findByIdOrThrowException(actorId);
		actor.setCharacter(character);
		actorRepository.save(actor);
	}

	public void deleteActorFromCharacter(long characterId, long actorId) {
		var actor = actorRepository.findByIdOrThrowException(actorId);
		if (actor.getCharacter() != null && actor.getCharacter().getId() == characterId) {
			actor.setCharacter(null);
			actorRepository.save(actor);
		}
	}
}
