package com.whalar.gameofthrones.repository;

import com.whalar.gameofthrones.exception.ResourceNotFoundException;
import com.whalar.gameofthrones.model.Actor;
import com.whalar.gameofthrones.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.whalar.gameofthrones.constant.Messages.ACTOR_NOT_FOUND;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

	List<Actor> findAllByCharacterId(long characterId);

	default Actor findByIdOrThrowException(long actorId) {
		return findById(actorId)
			.orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + " " + actorId));
	}

	default void deleteAllActorsFromCharacter(long characterId) {
		var actors = findAllByCharacterId(characterId);
		actors.forEach(actor -> actor.setCharacter(null));
		saveAll(actors);
	}

	default void addAllActorsToCharacter(Character character, List<Long> actorIds) {
		findAllById(actorIds).forEach(actor -> {
			actor.setCharacter(character);
			save(actor);
		});
	}
}
