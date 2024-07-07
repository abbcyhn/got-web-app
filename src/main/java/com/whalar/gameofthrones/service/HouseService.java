package com.whalar.gameofthrones.service;

import com.whalar.gameofthrones.constant.HouseName;
import com.whalar.gameofthrones.model.House;
import com.whalar.gameofthrones.repository.CharacterRepository;
import com.whalar.gameofthrones.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HouseService {

	private final HouseRepository houseRepository;
	private final CharacterRepository characterRepository;

	public void create(long characterId, HouseName houseName) {
		var character = characterRepository.findByIdOrThrowException(characterId);
		var house = new House();
		house.setHouseName(houseName);
		house.setCharacter(character);
		houseRepository.save(house);
	}

	public void delete(long characterId, HouseName houseName) {
		var house = houseRepository.findByCharacterIdAndHouseName(characterId, houseName);
		houseRepository.delete(house);
	}
}
