package com.whalar.gameofthrones.mapper;

import com.whalar.gameofthrones.common.BaseCharacter;
import com.whalar.gameofthrones.constant.ActionType;
import com.whalar.gameofthrones.constant.RelationType;
import com.whalar.gameofthrones.contract.CharacterSaveRequest;
import com.whalar.gameofthrones.contract.CharacterSearchResponseDto;
import com.whalar.gameofthrones.model.Character;
import com.whalar.gameofthrones.model.CharacterDocument;
import com.whalar.gameofthrones.rabbitmq.CharacterPayloadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CharacterMapper {

	private final ActionMapper actionMapper;
	private final ActorMapper actorMapper;
	private final AllyMapper allyMapper;
	private final HouseMapper houseMapper;
	private final RelationshipMapper relationshipMapper;

	public CharacterSearchResponseDto mapToCharacterSearchDto(Character character) {
		var dto = new CharacterSearchResponseDto();
		dto.setId(character.getId());
		dto.setActors(actorMapper.mapToActorDtoList(character.getId()));
		mapToBase(dto, character);
		return dto;
	}

	public CharacterDocument mapToCharacterDocument(CharacterPayloadDto characterPayloadDto) {
		var doc = new CharacterDocument();
		doc.setId(characterPayloadDto.getId());
		doc.setActors(actorMapper.mapToActorDocuments(Long.parseLong(characterPayloadDto.getId())));
		mapToBase(doc, characterPayloadDto);
		return doc;
	}

	public CharacterPayloadDto mapToCharacterPayloadDto(Character character) {
		var payload = new CharacterPayloadDto();
		payload.setId(character.getId().toString());
		payload.setActors(actorMapper.mapToActorPayloadDto(character.getId()));
		mapToBase(payload, character);
		return payload;
	}

	public List<CharacterSearchResponseDto> mapToCharacterSearchDtoList(List<CharacterDocument> documents) {
		return documents.stream()
			.map(this::mapToCharacterSearchResponseDto)
			.collect(Collectors.toList());
	}

	public Character mapToCharacterSearchDto(CharacterSaveRequest request) {
		var character = new Character();
		character.setName(request.getName());
		character.setNickname(request.getNickname());
		character.setLink(request.getLink());
		character.setRoyal(request.isRoyal());
		character.setKingsguard(request.isKingsguard());
		character.setImageFull(request.getImageFull());
		character.setImageThumb(request.getImageThumb());
		return character;
	}

	private CharacterSearchResponseDto mapToCharacterSearchResponseDto(CharacterDocument doc) {
		var dto = new CharacterSearchResponseDto();
		dto.setName(doc.getName());
		dto.setNickname(doc.getNickname());
		dto.setLink(doc.getLink());
		dto.setRoyal(doc.isRoyal());
		dto.setKingsguard(doc.isKingsguard());
		dto.setImageFull(doc.getImageFull());
		dto.setImageThumb(doc.getImageThumb());
		dto.setHouses(doc.getHouses());
		dto.setActors(actorMapper.mapToActorDtoList(doc.getActors()));
		dto.setAllies(doc.getAllies());
		dto.setParents(doc.getParents());
		dto.setParentOf(doc.getParentOf());
		dto.setSiblings(doc.getSiblings());
		dto.setMarriedEngaged(doc.getMarriedEngaged());
		dto.setAbducted(doc.getAbducted());
		dto.setAbductedBy(doc.getAbductedBy());
		dto.setKilled(doc.getKilled());
		dto.setKilledBy(doc.getKilledBy());
		dto.setServes(doc.getServes());
		dto.setServedBy(doc.getServedBy());
		dto.setGuardedBy(doc.getGuardedBy());
		dto.setGuardianOf(doc.getGuardianOf());
		return dto;
	}

	private void mapToBase(BaseCharacter base, Character character) {

		long characterId = character.getId();

		base.setName(character.getName());
		base.setNickname(character.getNickname());
		base.setLink(character.getLink());
		base.setRoyal(character.isRoyal());
		base.setKingsguard(character.isKingsguard());
		base.setImageFull(character.getImageFull());
		base.setImageThumb(character.getImageThumb());

		base.setHouses(houseMapper.mapToHouses(characterId));
		base.setAllies(allyMapper.mapToAllies(characterId));

		base.setParents(relationshipMapper.mapToRelationshipBy(characterId, RelationType.PARENT));
		base.setParentOf(relationshipMapper.mapToRelationshipTo(characterId, RelationType.PARENT));
		base.setSiblings(relationshipMapper.mapToRelationshipTo(characterId, RelationType.SIBLING));
		base.setMarriedEngaged(relationshipMapper.mapToRelationshipTo(characterId, RelationType.MARRIED_ENGAGED));

		base.setAbducted(actionMapper.mapToRelationshipTo(characterId, ActionType.ABDUCTED));
		base.setAbductedBy(actionMapper.mapToRelationshipBy(characterId, ActionType.ABDUCTED));
		base.setKilled(actionMapper.mapToRelationshipTo(characterId, ActionType.KILLED));
		base.setKilledBy(actionMapper.mapToRelationshipBy(characterId, ActionType.KILLED));
		base.setServes(actionMapper.mapToRelationshipTo(characterId, ActionType.SERVED));
		base.setServedBy(actionMapper.mapToRelationshipBy(characterId, ActionType.SERVED));
		base.setGuardianOf(actionMapper.mapToRelationshipTo(characterId, ActionType.GUARDED));
		base.setGuardedBy(actionMapper.mapToRelationshipBy(characterId, ActionType.GUARDED));
	}

	private void mapToBase(BaseCharacter base, CharacterPayloadDto character) {

		long characterId = Long.parseLong(character.getId());

		base.setName(character.getName());
		base.setNickname(character.getNickname());
		base.setLink(character.getLink());
		base.setRoyal(character.isRoyal());
		base.setKingsguard(character.isKingsguard());
		base.setImageFull(character.getImageFull());
		base.setImageThumb(character.getImageThumb());

		base.setHouses(houseMapper.mapToHouses(characterId));
		base.setAllies(allyMapper.mapToAllies(characterId));

		base.setParents(relationshipMapper.mapToRelationshipBy(characterId, RelationType.PARENT));
		base.setParentOf(relationshipMapper.mapToRelationshipTo(characterId, RelationType.PARENT));
		base.setSiblings(relationshipMapper.mapToRelationshipTo(characterId, RelationType.SIBLING));
		base.setMarriedEngaged(relationshipMapper.mapToRelationshipTo(characterId, RelationType.MARRIED_ENGAGED));

		base.setAbducted(actionMapper.mapToRelationshipTo(characterId, ActionType.ABDUCTED));
		base.setAbductedBy(actionMapper.mapToRelationshipBy(characterId, ActionType.ABDUCTED));
		base.setKilled(actionMapper.mapToRelationshipTo(characterId, ActionType.KILLED));
		base.setKilledBy(actionMapper.mapToRelationshipBy(characterId, ActionType.KILLED));
		base.setServes(actionMapper.mapToRelationshipTo(characterId, ActionType.SERVED));
		base.setServedBy(actionMapper.mapToRelationshipBy(characterId, ActionType.SERVED));
		base.setGuardianOf(actionMapper.mapToRelationshipTo(characterId, ActionType.GUARDED));
		base.setGuardedBy(actionMapper.mapToRelationshipBy(characterId, ActionType.GUARDED));
	}
}