package com.whalar.gameofthrones.repository;

import com.whalar.gameofthrones.exception.ResourceNotFoundException;
import com.whalar.gameofthrones.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import static com.whalar.gameofthrones.constant.Messages.CHARACTER_NOT_FOUND;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

	default Character findByIdOrThrowException(long characterId) {
		return findById(characterId)
			.orElseThrow(() -> new ResourceNotFoundException(CHARACTER_NOT_FOUND + " " + characterId));
	}
}
