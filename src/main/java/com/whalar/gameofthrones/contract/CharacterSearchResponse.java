package com.whalar.gameofthrones.contract;

import java.util.List;

public class CharacterSearchResponse extends BaseResponse<List<CharacterSearchResponseDto>> {

	public CharacterSearchResponse(List<CharacterSearchResponseDto> data) {
		super(data);
	}
}
