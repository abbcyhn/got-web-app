package com.whalar.gameofthrones.model;

import com.whalar.gameofthrones.common.BaseActor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class ActorDocument extends BaseActor {

	@Id
	private String id;
}
