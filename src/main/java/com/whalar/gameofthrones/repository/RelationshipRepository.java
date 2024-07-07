package com.whalar.gameofthrones.repository;

import com.whalar.gameofthrones.constant.RelationType;
import com.whalar.gameofthrones.model.Character;
import com.whalar.gameofthrones.model.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
	List<Relationship> findByCharacterIdAndRelationType(Long characterId, RelationType relationType);
	List<Relationship> findByRelationToIdAndRelationType(Long relationToId, RelationType relationType);


	default List<Character> findCharactersByRelationTo(long characterId, RelationType type) {
		return findByCharacterIdAndRelationType(characterId, type)
			.stream()
			.map(Relationship::getRelationTo)
			.toList();
	}

	default List<Character> findCharactersByRelationBy(Long characterId, RelationType type) {
		return findByRelationToIdAndRelationType(characterId, type)
			.stream()
			.map(Relationship::getCharacter)
			.toList();
	}
}
