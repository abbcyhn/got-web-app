package com.whalar.gameofthrones.mapper;

import com.whalar.gameofthrones.common.BaseActor;
import com.whalar.gameofthrones.contract.ActorFetchResponseDto;
import com.whalar.gameofthrones.model.ActiveSeason;
import com.whalar.gameofthrones.model.Actor;
import com.whalar.gameofthrones.model.ActorDocument;
import com.whalar.gameofthrones.rabbitmq.ActorPayloadDto;
import com.whalar.gameofthrones.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ActorMapper {

	private final ActorRepository actorRepository;

	public List<ActorPayloadDto> mapToActorPayloadDto(long characterId) {
		return mapToBase(characterId, actor -> {
			var doc = new ActorPayloadDto();
			doc.setId(actor.getId().toString());
			return doc;
		});
	}

	public List<ActorDocument> mapToActorDocuments(long characterId) {
		return mapToBase(characterId, actor -> {
			var doc = new ActorDocument();
			doc.setId(actor.getId().toString());
			return doc;
		});
	}

	public List<ActorFetchResponseDto> mapToActorDtoList(long characterId) {
		return mapToBase(characterId, actor -> {
			var dto = new ActorFetchResponseDto();
			dto.setId(actor.getId());
			return dto;
		});
	}

	public List<ActorFetchResponseDto> mapToActorDtoList(List<ActorDocument> docs) {
		return docs.stream()
			.map(doc -> {
				var actor = new ActorFetchResponseDto();
				actor.setId(Long.parseLong(doc.getId()));
				mapToBase(doc, actor);
				return actor;
			})
			.toList();
	}

	private  <T extends BaseActor> List<T> mapToBase(long characterId, Function<Actor, T> dtoSupplier) {
		var actors = actorRepository.findAllByCharacterId(characterId);
		return actors.stream()
			.map(actor -> {
				T base = dtoSupplier.apply(actor);
				base.setName(actor.getName());
				base.setLink(actor.getLink());
				base.setActiveSeasons(mapToSeasons(actor.getActiveSeasons()));
				return base;
			})
			.collect(Collectors.toList());
	}

	private <T extends BaseActor> T mapToBase(ActorDocument doc, T base) {
		base.setName(doc.getName());
		base.setLink(doc.getLink());
		base.setActiveSeasons(doc.getActiveSeasons());
		return base;
	}

	private List<Integer> mapToSeasons(List<ActiveSeason> activeSeasons) {
		return activeSeasons.stream()
			.map(ActiveSeason::getSeasonNumber)
			.collect(Collectors.toList());
	}
}
