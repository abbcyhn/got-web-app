package com.whalar.gameofthrones.utils;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.DeleteByQueryRequest;
import com.github.javafaker.Faker;
import com.whalar.gameofthrones.common.EasyRandomExclude;
import com.whalar.gameofthrones.common.EasyRandomFakerName;
import com.whalar.gameofthrones.common.EasyRandomFakerNickname;
import com.whalar.gameofthrones.common.EasyRandomFakerUrl;
import com.whalar.gameofthrones.contract.CharacterSaveRequest;
import com.whalar.gameofthrones.model.Actor;
import com.whalar.gameofthrones.model.Character;
import com.whalar.gameofthrones.repository.ActionRepository;
import com.whalar.gameofthrones.repository.ActorRepository;
import com.whalar.gameofthrones.repository.AllyRepository;
import com.whalar.gameofthrones.repository.CharacterRepository;
import com.whalar.gameofthrones.repository.HouseRepository;
import com.whalar.gameofthrones.repository.RelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.inject.Inject;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class TestDataGenerator {

	@Autowired
	ElasticsearchClient elasticsearchClient;

	@Autowired
	ActionRepository actionRepository;

	@Autowired
	ActorRepository actorRepository;

	@Autowired
	AllyRepository allyRepository;

	@Autowired
	CharacterRepository characterRepository;

	@Autowired
	HouseRepository houseRepository;

	@Autowired
	RelationshipRepository relationshipRepository;

	private static String getName() {
		return new Faker().name().fullName();
	}

	private static String getNickname() {
		return new Faker().funnyName().name();
	}

	private static String getUrl() {
		return "http://example.com/" + new Faker().internet().uuid();
	}

	public void reset() throws IOException {
		resetDb();
		resetElasticsearch();
	}

	public List<Character> generateCharacters(int count) {
		return generateCharacters(count, null, null, true);
	}

	public List<Character> generateCharacters(int count, String name, String nickname, boolean includeActors) {
		var characters = generateCharacters(count, false);
		characters.forEach(character -> {
			if (name != null) {
				character.setName(name);
			}
			if (nickname != null) {
				character.setNickname(nickname);
			}
		});
		characterRepository.saveAll(characters);

		if (includeActors) {
			characters.forEach(character -> {
				var actors = generateActors(1, null);
				actors.forEach(actor -> actor.setCharacter(character));
				actorRepository.saveAll(actors);
			});
		}

		return characters;
	}

	private List<Character> generateCharacters(int count, boolean overrideDefaultInitialization) {
		return getEasyRandom(overrideDefaultInitialization).objects(Character.class, count)
			.collect(Collectors.toList());
	}

	public List<Actor> generateActors(int count, String name) {
		var actors = generateActors(count, false);
		if (name != null) {
			actors.forEach(actor -> actor.setName(name));
		}
		return actors;
	}

	public List<Actor> generateActors(int count, boolean overrideDefaultInitialization) {
		var actors = getEasyRandom(overrideDefaultInitialization).objects(Actor.class, count)
			.collect(Collectors.toList());
		actors.forEach(actor -> actor.getActiveSeasons().forEach(s -> s.setActor(actor)));
		return actors;
	}

	public CharacterSaveRequestDetail generateCharacterSaveRequestDetail() {
		return generateCharacterSaveRequestDetail(null, null, null, true);
	}

	public CharacterSaveRequestDetail generateCharacterSaveRequestDetail(String characterName, String characterNickname,
	                                                                     String actorName, boolean includeActors) {
		int count = 1;

		var characterDetail = new CharacterSaveRequestDetail();
		characterDetail.setActors(generateActors(count, actorName));
		characterDetail.setAllies(generateCharacters(count, "", "", includeActors));
		characterDetail.setParents(generateCharacters(count, "", "", includeActors));
		characterDetail.setParentOf(generateCharacters(count, "", "", includeActors));
		characterDetail.setSiblings(generateCharacters(count, "", "", includeActors));
		characterDetail.setMarriedEngaged(generateCharacters(count, "", "", includeActors));
		characterDetail.setAbducted(generateCharacters(count, "", "", includeActors));
		characterDetail.setAbductedBy(generateCharacters(count, "", "", includeActors));
		characterDetail.setKilled(generateCharacters(count, "", "", includeActors));
		characterDetail.setKilledBy(generateCharacters(count, "", "", includeActors));
		characterDetail.setServes(generateCharacters(count, "", "", includeActors));
		characterDetail.setServedBy(generateCharacters(count, "", "", includeActors));
		characterDetail.setGuardianOf(generateCharacters(count, "", "", includeActors));
		characterDetail.setGuardedBy(generateCharacters(count, "", "", includeActors));

		persistCharacterSaveRequestDetail(characterDetail);

		var request = getEasyRandom(true)
			.objects(CharacterSaveRequest.class, 1)
			.toList()
			.getFirst();
		request.setName(characterName == null ? request.getName() : characterName);
		request.setNickname(characterNickname == null ? request.getNickname() : characterNickname);
		request.getActors().addAll(characterDetail.getActors().stream().map(Actor::getId).toList());
		request.getAllies().addAll(characterDetail.getAllies().stream().map(Character::getId).toList());
		request.getParents().addAll(characterDetail.getParents().stream().map(Character::getId).toList());
		request.getParentOf().addAll(characterDetail.getParentOf().stream().map(Character::getId).toList());
		request.getSiblings().addAll(characterDetail.getSiblings().stream().map(Character::getId).toList());
		request.getMarriedEngaged().addAll(characterDetail.getMarriedEngaged().stream().map(Character::getId).toList());
		request.getAbducted().addAll(characterDetail.getAbducted().stream().map(Character::getId).toList());
		request.getAbductedBy().addAll(characterDetail.getAbductedBy().stream().map(Character::getId).toList());
		request.getKilled().addAll(characterDetail.getKilled().stream().map(Character::getId).toList());
		request.getKilledBy().addAll(characterDetail.getKilledBy().stream().map(Character::getId).toList());
		request.getServes().addAll(characterDetail.getServes().stream().map(Character::getId).toList());
		request.getServedBy().addAll(characterDetail.getServedBy().stream().map(Character::getId).toList());
		request.getGuardianOf().addAll(characterDetail.getGuardianOf().stream().map(Character::getId).toList());
		request.getGuardedBy().addAll(characterDetail.getGuardedBy().stream().map(Character::getId).toList());

		characterDetail.setApiRequest(request);

		return characterDetail;
	}

	private void persistCharacterSaveRequestDetail(CharacterSaveRequestDetail characterDetails) {
		actorRepository.saveAll(characterDetails.getActors());
		characterRepository.saveAll(characterDetails.getAllies());
		characterRepository.saveAll(characterDetails.getParents());
		characterRepository.saveAll(characterDetails.getParentOf());
		characterRepository.saveAll(characterDetails.getSiblings());
		characterRepository.saveAll(characterDetails.getMarriedEngaged());
		characterRepository.saveAll(characterDetails.getAbducted());
		characterRepository.saveAll(characterDetails.getAbductedBy());
		characterRepository.saveAll(characterDetails.getKilled());
		characterRepository.saveAll(characterDetails.getKilledBy());
		characterRepository.saveAll(characterDetails.getServes());
		characterRepository.saveAll(characterDetails.getServedBy());
		characterRepository.saveAll(characterDetails.getGuardianOf());
		characterRepository.saveAll(characterDetails.getGuardedBy());
	}

	private EasyRandom getEasyRandom(boolean overrideDefaultInitialization) {
		return new EasyRandom(new EasyRandomParameters()
			.collectionSizeRange(1, 3)
			.overrideDefaultInitialization(overrideDefaultInitialization)
			.excludeField(field -> field.isAnnotationPresent(EasyRandomExclude.class))
			.randomize(field -> field.isAnnotationPresent(EasyRandomFakerName.class), TestDataGenerator::getName)
			.randomize(field -> field.isAnnotationPresent(EasyRandomFakerNickname.class), TestDataGenerator::getNickname)
			.randomize(field -> field.isAnnotationPresent(EasyRandomFakerUrl.class), TestDataGenerator::getUrl)
			.randomize(Integer.class, () -> new Random().nextInt(1, 6))
		);
	}

	private void resetDb() {
		allyRepository.deleteAll();
		relationshipRepository.deleteAll();
		actionRepository.deleteAll();
		houseRepository.deleteAll();
		actorRepository.deleteAll();
		characterRepository.deleteAll();
	}

	private void resetElasticsearch() throws IOException {
		var matchAll = new Query.Builder()
			.matchAll(new MatchAllQuery.Builder().build())
			.build();

		var deleteRequest = new DeleteByQueryRequest.Builder()
			.index("_all")
			.query(matchAll)
			.build();

		elasticsearchClient.deleteByQuery(deleteRequest)
			.deleted();
	}
}
