package com.whalar.gameofthrones.contract;

import com.whalar.gameofthrones.common.BaseActor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActorFetchResponseDto extends BaseActor {

	@Schema(description = "Id of the actor")
	private Long id;
}
