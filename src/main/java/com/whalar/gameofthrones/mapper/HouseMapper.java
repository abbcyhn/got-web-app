package com.whalar.gameofthrones.mapper;

import com.whalar.gameofthrones.constant.HouseName;
import com.whalar.gameofthrones.contract.CharacterSaveRequest;
import com.whalar.gameofthrones.model.Character;
import com.whalar.gameofthrones.model.House;
import com.whalar.gameofthrones.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HouseMapper {

	private final HouseRepository houseRepository;

	public List<HouseName> mapToHouses(long characterId) {
		return houseRepository.findAllByCharacterId(characterId)
			.stream()
			.map(House::getHouseName)
			.collect(Collectors.toList());
	}

	public List<House> mapToHouses(Character character, CharacterSaveRequest request) {
		var houses = request.getHouses();
		return houses.stream()
			.map(houseName -> {
				var house = new House();
				house.setHouseName(houseName);
				house.setCharacter(character);
				return house;
			})
			.collect(Collectors.toList());
	}
}
