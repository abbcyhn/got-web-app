package com.whalar.gameofthrones.service;

import com.whalar.gameofthrones.mapper.CharacterMapper;
import com.whalar.gameofthrones.repository.CharacterDocumentRepository;
import com.whalar.gameofthrones.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class DatabaseFillerService {

	private final JdbcTemplate jdbcTemplate;
	private final CharacterMapper characterMapper;
	private final CharacterRepository characterRepository;
	private final CharacterDocumentRepository characterDocumentRepository;

	public void execute() {
		if (characterRepository.count() > 300) {
			throw new UnsupportedOperationException("Database already filled");
		}
		loadDb();
		syncElasticsearch();
	}

	private void loadDb() {
		try {
			var resolver = new PathMatchingResourcePatternResolver();
			var sqls = resolver.getResources("*.sql");
			Arrays.sort(sqls, Comparator.comparing(Resource::getFilename));
			for (var sql : sqls) {
				executeSql(sql);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to load database", e);
		}
	}

	private void executeSql(Resource resource) {
		try (var reader = new BufferedReader(
			new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
			String sql = reader.lines().reduce("", (acc, line) -> acc + line + "\n");
			jdbcTemplate.execute(sql);
		} catch (Exception e) {
			throw new RuntimeException("Failed to execute SQL file: " + resource.getFilename(), e);
		}
	}

	private void syncElasticsearch() {
		var characters = characterRepository.findAll();
		characters.forEach(character -> {
			characterDocumentRepository.save(
				characterMapper.mapToCharacterDocument(character)
			);
		});
	}
}