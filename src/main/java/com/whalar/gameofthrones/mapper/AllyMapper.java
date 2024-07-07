package com.whalar.gameofthrones.mapper;

import com.whalar.gameofthrones.contract.CharacterSaveRequest;
import com.whalar.gameofthrones.model.Ally;
import com.whalar.gameofthrones.model.Character;
import com.whalar.gameofthrones.repository.AllyRepository;
import com.whalar.gameofthrones.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AllyMapper {

	private final AllyRepository allyRepository;
	private final CharacterRepository characterRepository;

	public List<String> mapToAllies(long characterId) {
		return allyRepository.findAllByCharacterId(characterId)
			.stream()
			.map(ally -> ally.getAlly().getName())
			.collect(Collectors.toList());
	}

	public List<Ally> mapToAllies(Character character, CharacterSaveRequest request) {
		var allies = request.getAllies();

		return allies.stream()
			.map(allyId -> {
				var ally = new Ally();
				var allyCharacter = characterRepository.findByIdOrThrowException(allyId);
				ally.setAlly(allyCharacter);
				ally.setCharacter(character);
				return ally;
			})
			.collect(Collectors.toList());
	}
}
