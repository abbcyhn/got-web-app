package com.whalar.gameofthrones.mapper;

import com.whalar.gameofthrones.constant.RelationType;
import com.whalar.gameofthrones.contract.CharacterSaveRequest;
import com.whalar.gameofthrones.model.Character;
import com.whalar.gameofthrones.model.Relationship;
import com.whalar.gameofthrones.repository.CharacterRepository;
import com.whalar.gameofthrones.repository.RelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RelationshipMapper {

	private final CharacterRepository characterRepository;
	private final RelationshipRepository relationshipRepository;

	public List<Relationship> mapToRelationships(Character character, CharacterSaveRequest request) {
		var relationships = new ArrayList<Relationship>();

		relationships.addAll(mapToParents(character, request.getParents()));
		relationships.addAll(mapToParentOf(character, request.getParentOf()));
		relationships.addAll(mapToSiblings(character, request.getSiblings()));
		relationships.addAll(mapToMarriedEngaged(character, request.getMarriedEngaged()));

		return relationships;
	}

	public List<String> mapToRelationshipTo(long characterId, RelationType relationType) {
		return relationshipRepository.findCharactersByRelationTo(characterId, relationType)
			.stream()
			.map(Character::getName)
			.collect(Collectors.toList());
	}

	public List<String> mapToRelationshipBy(long characterId, RelationType relationType) {
		return relationshipRepository.findCharactersByRelationBy(characterId, relationType)
			.stream()
			.map(Character::getName)
			.collect(Collectors.toList());
	}

	private List<Relationship> mapToParents(Character character, List<Long> parents) {
		return parents.stream()
			.map(relationTo -> {
				var relationToCharacter = characterRepository.findByIdOrThrowException(relationTo);
				var relationship = new Relationship();
				relationship.setRelationTo(character);
				relationship.setRelationType(RelationType.PARENT);
				relationship.setCharacter(relationToCharacter);
				return relationship;
			})
			.collect(Collectors.toList());
	}

	private List<Relationship> mapToParentOf(Character character, List<Long> parentOf) {
		return parentOf.stream()
			.map(relationTo -> {
				var relationToCharacter = characterRepository.findByIdOrThrowException(relationTo);
				var relationship = new Relationship();
				relationship.setRelationTo(relationToCharacter);
				relationship.setRelationType(RelationType.PARENT);
				relationship.setCharacter(character);
				return relationship;
			})
			.collect(Collectors.toList());
	}

	private List<Relationship> mapToSiblings(Character character, List<Long> siblings) {
		return siblings.stream()
			.map(relationTo -> {
				var relationToCharacter = characterRepository.findByIdOrThrowException(relationTo);
				var relationship = new Relationship();
				relationship.setRelationTo(relationToCharacter);
				relationship.setRelationType(RelationType.SIBLING);
				relationship.setCharacter(character);
				return relationship;
			})
			.collect(Collectors.toList());
	}

	private List<Relationship> mapToMarriedEngaged(Character character, List<Long> marriedEngaged) {
		return marriedEngaged.stream()
			.map(relationTo -> {
				var relationToCharacter = characterRepository.findByIdOrThrowException(relationTo);
				var relationship = new Relationship();
				relationship.setRelationTo(relationToCharacter);
				relationship.setRelationType(RelationType.MARRIED_ENGAGED);
				relationship.setCharacter(character);
				return relationship;
			})
			.collect(Collectors.toList());
	}
}
