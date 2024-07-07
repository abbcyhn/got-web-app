package com.whalar.gameofthrones.repository;

import com.whalar.gameofthrones.constant.ActionType;
import com.whalar.gameofthrones.model.Action;
import com.whalar.gameofthrones.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

	List<Action> findByCharacterIdAndActionType(Long characterId, ActionType actionType);
	List<Action> findByActionToIdAndActionType(Long actionToId, ActionType actionType);

	default List<Character> findCharactersByActionTo(long characterId, ActionType actionType) {
		return findByCharacterIdAndActionType(characterId, actionType)
			.stream()
			.map(Action::getActionTo)
			.toList();
	}

	default List<Character> findCharactersByActionBy(long characterId, ActionType actionType) {
		return findByActionToIdAndActionType(characterId, actionType)
			.stream()
			.map(Action::getCharacter)
			.toList();
	}
}
