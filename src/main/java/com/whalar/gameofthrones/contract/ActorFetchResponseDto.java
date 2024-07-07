package com.whalar.gameofthrones.contract;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.whalar.gameofthrones.common.BaseActor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder("id")
public class ActorFetchResponseDto extends BaseActor {

	@Schema(description = "Id of the actor")
	private Long id;
}
