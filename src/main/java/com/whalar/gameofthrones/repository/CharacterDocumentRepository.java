package com.whalar.gameofthrones.repository;

import com.whalar.gameofthrones.model.CharacterDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public interface CharacterDocumentRepository extends ElasticsearchRepository<CharacterDocument, String> {

	@Query("{\"bool\": {\"must\": [" +
		"{\"wildcard\": {\"name\": \"*?0*\"}}," +
		"{\"wildcard\": {\"nickname\": \"*?1*\"}}," +
		"{\"nested\": {\"path\": \"actors\", " +
		"\"query\": {\"bool\": {\"must\": [" +
		"{\"wildcard\": {\"actors.name\": \"*?2*\"}}" +
		"]}}}}" +
	"]}}")
	default List<CharacterDocument> findByNameContainingAndNicknameContainingAndActorsNameContaining(
		String name, String nickname, String actorName) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
				findAll().iterator(), Spliterator.ORDERED), false)
			.filter(c -> c.getName().contains(name))
			.filter(c -> c.getNickname().contains(nickname))
			.filter(c -> c.getActors().stream().anyMatch(actor ->
				actor.getName().contains(actorName)))
			.toList();
	}
}