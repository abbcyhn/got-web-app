package com.whalar.gameofthrones.rabbitmq;

import com.whalar.gameofthrones.common.BaseCharacter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Schema(description = "Character payload model")
public class CharacterPayloadDto extends BaseCharacter {

	@Id
	@Schema(description = "Unique identifier for the character")
	private String id;

	@Schema(description = "List of actors who portrayed the character")
	private List<ActorPayloadDto> actors = new ArrayList<>();
}
