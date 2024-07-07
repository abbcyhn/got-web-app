package com.whalar.gameofthrones.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class BaseActor {

	@Schema(description = "Name of the actor")
	private String name;

	@Schema(description = "Link related to the actor")
	private String link;

	@Schema(description = "List of active seasons in which the actor appeared")
	private List<Integer> activeSeasons;
}
