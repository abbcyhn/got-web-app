package com.whalar.gameofthrones.rabbitmq;

import com.whalar.gameofthrones.common.BaseActor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Schema(description = "Actor payload model")
public class ActorPayloadDto extends BaseActor {

	@Id
	private String id;
}
