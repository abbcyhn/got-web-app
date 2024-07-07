package com.whalar.gameofthrones.contract;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseResponse<T> {
	private T data;
	private List<ErrorResponse> errors;

	public BaseResponse(T data) {
		this.data = data;
	}

	public BaseResponse(List<ErrorResponse> errors) {
		this.errors = errors;
	}
}
