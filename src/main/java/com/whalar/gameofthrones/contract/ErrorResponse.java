package com.whalar.gameofthrones.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

	@Schema(description = "Error code indicating the type of error")
	private String code;

	@Schema(description = "Error message providing details about the error")
	private String message;
}
