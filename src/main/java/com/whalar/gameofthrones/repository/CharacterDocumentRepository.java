package com.whalar.gameofthrones.repository;

import ch.qos.logback.core.util.StringUtil;
import com.whalar.gameofthrones.contract.CharacterSearchRequest;
import com.whalar.gameofthrones.model.CharacterDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public interface CharacterDocumentRepository extends ElasticsearchRepository<CharacterDocument, String> {

	default List<CharacterDocument> findAllByRequest(CharacterSearchRequest request) {
		var docs = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
			findAll().iterator(), Spliterator.ORDERED), false).toList();

		if (!StringUtil.isNullOrEmpty(request.getName())) {
			docs = docs.stream().filter(d -> d.getName().toLowerCase()
				.contains(request.getName().toLowerCase())).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getNickname())) {
			docs = docs.stream().filter(d -> d.getNickname().toLowerCase()
				.contains(request.getNickname().toLowerCase())).toList();
		}

		if (request.getRoyal() != null) {
			docs = docs.stream().filter(d -> d.isRoyal() == request.getRoyal()).toList();
		}

		if (request.getKingsguard() != null) {
			docs = docs.stream().filter(d -> d.isKingsguard() == request.getKingsguard()).toList();
		}

		if (request.getHouseName() != null) {
			docs = docs.stream().filter(d -> d.getHouses().stream()
				.anyMatch(a -> a.equals(request.getHouseName()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getActorName())) {
			docs = docs.stream().filter(d -> d.getActors().stream()
				.anyMatch(a -> a.getName().toLowerCase()
					.contains(request.getActorName().toLowerCase()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getAllyName())) {
			docs = docs.stream().filter(d -> d.getAllies().stream()
				.anyMatch(a -> a.toLowerCase()
					.contains(request.getAllyName().toLowerCase()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getParentName())) {
			docs = docs.stream().filter(d -> d.getParents().stream()
				.anyMatch(a -> a.toLowerCase()
					.contains(request.getParentName().toLowerCase()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getParentOf())) {
			docs = docs.stream().filter(d -> d.getParentOf().stream()
				.anyMatch(a -> a.toLowerCase()
					.contains(request.getParentOf().toLowerCase()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getSibling())) {
			docs = docs.stream().filter(d -> d.getSiblings().stream()
				.anyMatch(a -> a.toLowerCase()
					.contains(request.getSibling().toLowerCase()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getMarriedEngaged())) {
			docs = docs.stream().filter(d -> d.getMarriedEngaged().stream()
				.anyMatch(a -> a.toLowerCase()
					.contains(request.getMarriedEngaged().toLowerCase()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getAbducted())) {
			docs = docs.stream().filter(d -> d.getAbducted().stream()
				.anyMatch(a -> a.toLowerCase()
					.contains(request.getAbducted().toLowerCase()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getAbductedBy())) {
			docs = docs.stream().filter(d -> d.getAbductedBy().stream()
				.anyMatch(a -> a.toLowerCase()
					.contains(request.getAbductedBy().toLowerCase()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getKilled())) {
			docs = docs.stream().filter(d -> d.getKilled().stream()
				.anyMatch(a -> a.toLowerCase()
					.contains(request.getKilled().toLowerCase()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getKilledBy())) {
			docs = docs.stream().filter(d -> d.getKilledBy().stream()
				.anyMatch(a -> a.toLowerCase()
					.contains(request.getKilledBy().toLowerCase()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getServes())) {
			docs = docs.stream().filter(d -> d.getServes().stream()
				.anyMatch(a -> a.toLowerCase()
					.contains(request.getServes().toLowerCase()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getServedBy())) {
			docs = docs.stream().filter(d -> d.getServedBy().stream()
				.anyMatch(a -> a.toLowerCase()
					.contains(request.getServedBy().toLowerCase()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getGuardedBy())) {
			docs = docs.stream().filter(d -> d.getGuardedBy().stream()
				.anyMatch(a -> a.toLowerCase()
					.contains(request.getGuardedBy().toLowerCase()))).toList();
		}

		if (!StringUtil.isNullOrEmpty(request.getGuardianOf())) {
			docs = docs.stream().filter(d -> d.getGuardianOf().stream()
				.anyMatch(a -> a.toLowerCase()
					.contains(request.getGuardianOf().toLowerCase()))).toList();
		}

		return docs;
	}
}