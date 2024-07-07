package com.whalar.gameofthrones.repository;

import com.whalar.gameofthrones.constant.HouseName;
import com.whalar.gameofthrones.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

	List<House> findAllByCharacterId(long characterId);
	House findByCharacterIdAndHouseName(long characterId, HouseName houseName);
}
