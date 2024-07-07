package com.whalar.gameofthrones.controller;

import com.whalar.gameofthrones.contract.CharacterFetchResponse;
import com.whalar.gameofthrones.contract.CharacterSaveRequest;
import com.whalar.gameofthrones.contract.CharacterSearchRequest;
import com.whalar.gameofthrones.contract.CharacterSearchResponse;
import com.whalar.gameofthrones.contract.ErrorResponse;
import com.whalar.gameofthrones.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/characters")
@Tag(name = "Character Management", description = "Endpoints for managing characters in the system")
public class CharacterController {

	private final CharacterService characterService;

	@GetMapping("/")
	@Operation(summary = "Search Characters By Terms")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully fetched",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = CharacterSearchResponse.class)))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class))))
	})
	public ResponseEntity<CharacterSearchResponse> findCharacters(CharacterSearchRequest request) {
		var dto = characterService.getAll(request);
		return ResponseEntity.ok(new CharacterSearchResponse(dto));
	}

	@GetMapping("/{characterId}")
	@Operation(summary = "Find Character By Id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully fetched",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = CharacterFetchResponse.class)))),
		@ApiResponse(responseCode = "404", description = "Not found",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class)))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class))))
	})
	public ResponseEntity<CharacterFetchResponse> findCharacterById(
		@Parameter(description = "Character Id") @PathVariable long characterId) {
		var dto = characterService.getById(characterId);
		return ResponseEntity.ok(new CharacterFetchResponse(dto));
	}

	@PostMapping("/")
	@Operation(summary = "Create Character")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Location of created entity",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = URI.class)))),
		@ApiResponse(responseCode = "400", description = "Validation error",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class)))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class))))
	})
	public ResponseEntity<URI> createCharacter(
		@Parameter(description = "Character Detail") @Valid @RequestBody CharacterSaveRequest request) {
		var location = characterService.create(request);
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{characterId}")
	@Operation(summary = "Update Character")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Location of updated entity",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = URI.class)))),
		@ApiResponse(responseCode = "400", description = "Validation error",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class)))),
		@ApiResponse(responseCode = "404", description = "Not found",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class)))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class))))
	})
	public ResponseEntity<URI> updateCharacter(
		@Parameter(description = "Character Id") @PathVariable long characterId,
		@Parameter(description = "Character Detail") @Valid @RequestBody CharacterSaveRequest request) {
		var location = characterService.update(characterId, request);
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/{characterId}")
	@Operation(summary = "Delete Character")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Successfully deleted"),
		@ApiResponse(responseCode = "404", description = "Not found",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class)))),
		@ApiResponse(responseCode = "500", description = "Internal server error",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class))))
	})
	public ResponseEntity<?> deleteCharacter(
		@Parameter(description = "Character Id") @PathVariable long characterId) {
		characterService.delete(characterId);
		return ResponseEntity.noContent().build();
	}
}
