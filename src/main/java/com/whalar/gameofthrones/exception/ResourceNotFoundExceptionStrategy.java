package com.whalar.gameofthrones.exception;

import com.whalar.gameofthrones.contract.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ResourceNotFoundExceptionStrategy extends AbstractExceptionHandlingStrategy {

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.NOT_FOUND;
	}

	@Override
	public List<ErrorResponse> getErrors(Exception e) {
		return toErrors(e);
	}
}
