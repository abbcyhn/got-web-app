package com.whalar.gameofthrones.repository;

import com.whalar.gameofthrones.model.Ally;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllyRepository extends JpaRepository<Ally, Long> {

	List<Ally> findAllByCharacterId(long characterId);
	Ally findByCharacterIdAndAllyId(long characterId, long allyId);
}
