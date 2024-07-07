package com.whalar.gameofthrones.mapper;

import com.whalar.gameofthrones.constant.ActionType;
import com.whalar.gameofthrones.contract.CharacterSaveRequest;
import com.whalar.gameofthrones.model.Action;
import com.whalar.gameofthrones.model.Character;
import com.whalar.gameofthrones.repository.ActionRepository;
import com.whalar.gameofthrones.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ActionMapper {

	private final ActionRepository actionRepository;
	private final CharacterRepository characterRepository;

	public List<Action> mapToActions(Character character, CharacterSaveRequest request) {
		var actions = new ArrayList<Action>();

		actions.addAll(mapToActions(character, request.getAbducted(), ActionType.ABDUCTED));
		actions.addAll(mapToActionsBy(character, request.getAbductedBy(), ActionType.ABDUCTED));
		actions.addAll(mapToActions(character, request.getKilled(), ActionType.KILLED));
		actions.addAll(mapToActionsBy(character, request.getKilledBy(), ActionType.KILLED));
		actions.addAll(mapToActions(character, request.getServes(), ActionType.SERVED));
		actions.addAll(mapToActionsBy(character, request.getServedBy(), ActionType.SERVED));
		actions.addAll(mapToActions(character, request.getGuardianOf(), ActionType.GUARDED));
		actions.addAll(mapToActionsBy(character, request.getGuardedBy(), ActionType.GUARDED));

		return actions;
	}

	public List<String> mapToRelationshipTo(long characterId, ActionType actionType) {
		return actionRepository.findCharactersByActionTo(characterId, actionType)
			.stream()
			.map(Character::getName)
			.collect(Collectors.toList());
	}

	public List<String> mapToRelationshipBy(long characterId, ActionType actionType) {
		return actionRepository.findCharactersByActionBy(characterId, actionType)
			.stream()
			.map(Character::getName)
			.collect(Collectors.toList());
	}

	private List<Action> mapToActions(Character character, List<Long> actions, ActionType actionType) {
		return actions.stream()
			.map(actionTo -> {
				var actionToCharacter = characterRepository.findByIdOrThrowException(actionTo);
				var action = new Action();
				action.setActionTo(actionToCharacter);
				action.setActionType(actionType);
				action.setCharacter(character);
				return action;
			})
			.collect(Collectors.toList());
	}

	private List<Action> mapToActionsBy(Character character, List<Long> actions, ActionType actionType) {
		return actions.stream()
			.map(actionTo -> {
				var actionToCharacter = characterRepository.findByIdOrThrowException(actionTo);
				var action = new Action();
				action.setActionTo(character);
				action.setActionType(actionType);
				action.setCharacter(actionToCharacter);
				return action;
			})
			.collect(Collectors.toList());
	}
}
