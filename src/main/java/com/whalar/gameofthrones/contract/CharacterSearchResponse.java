package com.whalar.gameofthrones.contract;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder("count")
public class CharacterSearchResponse extends BaseResponse<List<CharacterSearchResponseDto>> {

	public CharacterSearchResponse(List<CharacterSearchResponseDto> data) {
		super(data);
	}

	public int getCount() {
		return getData().size();
	}
}
