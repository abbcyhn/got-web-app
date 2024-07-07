package com.whalar.gameofthrones.exception;

import com.whalar.gameofthrones.contract.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.util.List;

public class DefaultExceptionStrategy extends AbstractExceptionHandlingStrategy {
	@Override
	public HttpStatus getStatus() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Override
	public List<ErrorResponse> getErrors(Exception e) {
		return toErrors(e);
	}
}
