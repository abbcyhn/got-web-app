package com.whalar.gameofthrones.utils;

import com.whalar.gameofthrones.constant.ActionType;
import com.whalar.gameofthrones.constant.RelationType;
import com.whalar.gameofthrones.model.Actor;
import com.whalar.gameofthrones.model.Ally;
import com.whalar.gameofthrones.model.House;
import com.whalar.gameofthrones.repository.ActionRepository;
import com.whalar.gameofthrones.repository.CharacterRepository;
import com.whalar.gameofthrones.repository.RelationshipRepository;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class AssertionsHelper {

	@Autowired
	CharacterRepository characterRepository;

	@Autowired
	RelationshipRepository relationshipRepository;

	@Autowired
	ActionRepository actionRepository;

	public void assertSaveEquals(CharacterSaveRequestDetail requestDetail, Response response) {
		var request = requestDetail.getApiRequest();

		// Assert response header
		String locationHeader = response.getHeader("Location");
		Long id = Long.parseLong(locationHeader.replaceAll(".*/api/v1/characters/(\\d+)", "$1"));

		// Assert character is created
		var characterOptional = characterRepository.findById(id);
		assertTrue(characterOptional.isPresent());

		// Assert character
		var character = characterOptional.get();
		assertEquals(id, character.getId());
		assertEquals(request.getName(), character.getName());
		assertEquals(request.getNickname(), character.getNickname());
		assertEquals(request.getLink(), character.getLink());
		assertEquals(request.isRoyal(), character.isRoyal());
		assertEquals(request.isKingsguard(), character.isKingsguard());
		assertEquals(request.getImageFull(), character.getImageFull());
		assertEquals(request.getImageThumb(), character.getImageThumb());

		assertIterableEquals(request.getHouses(), character.getHouses().stream().map(House::getHouseName).toList());
		assertIterableEquals(requestDetail.getAllies(), character.getAllies().stream().map(Ally::getAlly).toList());
		assertIterableEquals(request.getActors(), character.getActors().stream().map(Actor::getId).toList());

		assertIterableEquals(requestDetail.getParents(),
			relationshipRepository.findCharactersByRelationBy(character.getId(), RelationType.PARENT));

		assertIterableEquals(requestDetail.getParentOf(),
			relationshipRepository.findCharactersByRelationTo(character.getId(), RelationType.PARENT));

		assertIterableEquals(requestDetail.getSiblings(),
			relationshipRepository.findCharactersByRelationTo(character.getId(), RelationType.SIBLING));

		assertIterableEquals(requestDetail.getMarriedEngaged(),
			relationshipRepository.findCharactersByRelationTo(character.getId(), RelationType.MARRIED_ENGAGED));

		assertIterableEquals(requestDetail.getAbducted(),
			actionRepository.findCharactersByActionTo(character.getId(), ActionType.ABDUCTED));

		assertIterableEquals(requestDetail.getAbductedBy(),
			actionRepository.findCharactersByActionBy(character.getId(), ActionType.ABDUCTED));

		assertIterableEquals(requestDetail.getKilled(),
			actionRepository.findCharactersByActionTo(character.getId(), ActionType.KILLED));

		assertIterableEquals(requestDetail.getKilledBy(),
			actionRepository.findCharactersByActionBy(character.getId(), ActionType.KILLED));

		assertIterableEquals(requestDetail.getServes(),
			actionRepository.findCharactersByActionTo(character.getId(), ActionType.SERVED));

		assertIterableEquals(requestDetail.getServedBy(),
			actionRepository.findCharactersByActionBy(character.getId(), ActionType.SERVED));

		assertIterableEquals(requestDetail.getGuardianOf(),
			actionRepository.findCharactersByActionTo(character.getId(), ActionType.GUARDED));

		assertIterableEquals(requestDetail.getGuardedBy(),
			actionRepository.findCharactersByActionBy(character.getId(), ActionType.GUARDED));
	}

	public void assertDeleteEquals(long charaterId) {
		assertFalse(characterRepository.findById(charaterId).isPresent());
	}
}
