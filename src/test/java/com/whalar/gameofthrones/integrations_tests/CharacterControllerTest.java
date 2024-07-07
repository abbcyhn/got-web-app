package com.whalar.gameofthrones.integrations_tests;

import com.whalar.gameofthrones.contract.CharacterSaveRequest;
import com.whalar.gameofthrones.utils.AssertionsHelper;
import com.whalar.gameofthrones.utils.TestDataGenerator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.time.Duration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class CharacterControllerTest {

	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
		"postgres:16.1-alpine");

	static ElasticsearchContainer elasticsearch = new ElasticsearchContainer(
		"elasticsearch:8.8.2")
		.withEnv("discovery.type", "single-node")
		.withEnv("xpack.security.enabled", "false");

	static RabbitMQContainer rabbitMQ = new RabbitMQContainer(
		"rabbitmq:3-management");

	@Autowired
	TestDataGenerator testDataGenerator;

	@Autowired
	AssertionsHelper assertionsHelper;

	@LocalServerPort
	private Integer port;

	@BeforeAll
	static void beforeAll() {
		postgres.start();
		elasticsearch.start();
		rabbitMQ.start();
	}

	@AfterAll
	static void afterAll() {
		postgres.stop();
		elasticsearch.stop();
		rabbitMQ.stop();
	}

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);

		registry.add("spring.elasticsearch.uris", () -> "http://" + elasticsearch.getHttpHostAddress());

		registry.add("spring.rabbitmq.host", rabbitMQ::getHost);
		registry.add("spring.rabbitmq.port", rabbitMQ::getAmqpPort);
		registry.add("spring.rabbitmq.username", rabbitMQ::getAdminUsername);
		registry.add("spring.rabbitmq.password", rabbitMQ::getAdminPassword);
	}

	@BeforeEach
	void setUp() throws IOException {
		RestAssured.baseURI = "http://localhost:" + port;
		testDataGenerator.reset();
	}

	@ParameterizedTest
	@CsvSource({
		"B, , , 2",
		"Sam, , , 1",
		"Sam, Greyjoy, , 0",
		"Greyjoy, Greyjoy, , 1",
		", , Willi, 2",
	})
	void testGetAllCharacters_ReturnsAllCharacters(String searchName, String searchNickname, String searchActorName,
		int expectedCount) {

		createCharacter(testDataGenerator
			.generateCharacterSaveRequestDetail("Baby Sam", "", "William Wilson", false)
			.getApiRequest());

		createCharacter(testDataGenerator
			.generateCharacterSaveRequestDetail("Balon Greyjoy", "Greyjoy", "Patrick Willi", false)
			.getApiRequest());

		await().atMost(Duration.ofMillis(5000)).untilAsserted(() -> {
			given()
				.contentType(ContentType.JSON)
				.queryParam("name", searchName)
				.queryParam("nickname", searchNickname)
				.queryParam("actorName", searchActorName)
				.when()
				.get("/api/v1/characters/")
				.then()
				.statusCode(200)
				.body("data", hasSize(expectedCount));
		});
	}

	@Test
	void testGetCharacterById_ExistingId_ReturnsCharacter() {
		var character = testDataGenerator.generateCharacters(1).getFirst();

		given()
			.contentType(ContentType.JSON)
			.when()
			.get("/api/v1/characters/{characterId}", character.getId())
			.then()
			.statusCode(200)
			.body("data", notNullValue())
			.body("data.id", equalTo(Math.toIntExact(character.getId())));
	}

	@Test
	void testGetCharacterById_NonExistingId_ReturnsNotFound() {
		given()
			.contentType(ContentType.JSON)
			.when()
			.get("/api/v1/characters/{characterId}", Long.MIN_VALUE)
			.then()
			.statusCode(404)
			.body("data", nullValue())
			.body("errors", notNullValue());
	}

	@Test
	void testCreateCharacter_ValidInput_CreatesCharacter() {
		var requestDetail = testDataGenerator.generateCharacterSaveRequestDetail();

		var response = createCharacter(requestDetail.getApiRequest());

		assertionsHelper.assertSaveEquals(requestDetail, response);
	}

	@Test
	void testUpdateCharacter_ValidInput_UpdatesCharacter() {
		var character = testDataGenerator.generateCharacters(1).getFirst();
		var requestDetail = testDataGenerator.generateCharacterSaveRequestDetail();

		var response = given()
			.contentType(ContentType.JSON)
			.body(requestDetail.getApiRequest())
			.when()
			.put("/api/v1/characters/{characterId}", character.getId())
			.then()
			.statusCode(201)
			.header("Location", matchesPattern(".*/api/v1/characters/\\d+"))
			.extract()
			.response();

		assertionsHelper.assertSaveEquals(requestDetail, response);
	}

	@Test
	void testDeleteCharacterById_ExistingId_DeletesCharacter() {
		var character = testDataGenerator.generateCharacters(1).getFirst();

		given()
			.contentType(ContentType.JSON)
			.when()
			.delete("/api/v1/characters/{characterId}", character.getId())
			.then()
			.statusCode(204);

		assertionsHelper.assertDeleteEquals(character.getId());
	}

	Response createCharacter(CharacterSaveRequest request) {
		return given()
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.post("/api/v1/characters/")
			.then()
			.statusCode(201)
			.header("Location", matchesPattern(".*/api/v1/characters/\\d+"))
			.extract()
			.response();
	}
}