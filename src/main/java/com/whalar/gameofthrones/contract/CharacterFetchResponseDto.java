package com.whalar.gameofthrones.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.whalar.gameofthrones.common.BaseCharacter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonPropertyOrder("id")
public class CharacterFetchResponseDto extends BaseCharacter {

	@Schema(description = "Unique identifier for the character")
	private Long id;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of actors who portrayed the character")
	private List<ActorFetchResponseDto> actors = new ArrayList<>();
}
