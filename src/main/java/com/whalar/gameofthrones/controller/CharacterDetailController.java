package com.whalar.gameofthrones.controller;

import com.whalar.gameofthrones.constant.HouseName;
import com.whalar.gameofthrones.contract.ErrorResponse;
import com.whalar.gameofthrones.service.ActorService;
import com.whalar.gameofthrones.service.AllyService;
import com.whalar.gameofthrones.service.HouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/characters/{characterId}")
@Tag(name = "Character Management", description = "Endpoints for managing characters in the system")
public class CharacterDetailController {

	private final ActorService actorService;
	private final AllyService allyService;
	private final HouseService houseService;

	@PostMapping("/actors/{actorId}")
	@Operation(summary = "Add Actor")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully added",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = URI.class)))),
		@ApiResponse(responseCode = "404", description = "Not found",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class)))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class))))
	})
	public ResponseEntity<?> createActor(
		@Parameter(description = "Character Id") @PathVariable long characterId,
		@Parameter(description = "Actor Id") @PathVariable long actorId) {
		actorService.addActorToCharacter(characterId, actorId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/actors/{actorId}")
	@Operation(summary = "Delete Actor")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Successfully deleted"),
		@ApiResponse(responseCode = "404", description = "Not found",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class)))),
		@ApiResponse(responseCode = "500", description = "Internal server error",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class))))
	})
	public ResponseEntity<URI> deleteActor(
		@Parameter(description = "Character Id") @PathVariable long characterId,
		@Parameter(description = "Actor Id") @PathVariable long actorId) {
		actorService.deleteActorFromCharacter(characterId, actorId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/allies/{allyId}")
	@Operation(summary = "Add Ally")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully added",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = URI.class)))),
		@ApiResponse(responseCode = "404", description = "Not found",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class)))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class))))
	})
	public ResponseEntity<?> createAlly(
		@Parameter(description = "Character Id") @PathVariable long characterId,
		@Parameter(description = "Ally Id") @PathVariable long allyId) {
		allyService.create(characterId, allyId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/allies/{allyId}")
	@Operation(summary = "Delete Ally")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Successfully deleted"),
		@ApiResponse(responseCode = "404", description = "Not found",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class)))),
		@ApiResponse(responseCode = "500", description = "Internal server error",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class))))
	})
	public ResponseEntity<URI> deleteAlly(
		@Parameter(description = "Character Id") @PathVariable long characterId,
		@Parameter(description = "Ally Id") @PathVariable long allyId) {
		allyService.delete(characterId, allyId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/houses/{houseName}")
	@Operation(summary = "Add House")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully added",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = URI.class)))),
		@ApiResponse(responseCode = "404", description = "Not found",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class)))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class))))
	})
	public ResponseEntity<?> createHouse(
		@Parameter(description = "Character Id") @PathVariable long characterId,
		@Parameter(description = "House Id") @PathVariable HouseName houseName) {
		houseService.create(characterId, houseName);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/houses/{houseName}")
	@Operation(summary = "Delete House")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Successfully deleted"),
		@ApiResponse(responseCode = "404", description = "Not found",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class)))),
		@ApiResponse(responseCode = "500", description = "Internal server error",
			content = @Content(array = @ArraySchema(
				schema = @Schema(implementation = ErrorResponse.class))))
	})
	public ResponseEntity<URI> deleteHouse(
		@Parameter(description = "Character Id") @PathVariable long characterId,
		@Parameter(description = "House Id") @PathVariable HouseName houseName) {
		houseService.delete(characterId, houseName);
		return ResponseEntity.noContent().build();
	}
}
