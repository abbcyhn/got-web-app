package com.whalar.gameofthrones.contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterSearchRequest {

	private String name = "";
	private String nickname = "";
	private String actorName = "";
}
