package com.whalar.gameofthrones.exception;

import com.whalar.gameofthrones.contract.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public class MethodArgumentNotValidExceptionStrategy extends AbstractExceptionHandlingStrategy {
	@Override
	public HttpStatus getStatus() {
		return HttpStatus.BAD_REQUEST;
	}

	@Override
	public List<ErrorResponse> getErrors(Exception e) {
		if (e instanceof MethodArgumentNotValidException) {
			return toErrors((MethodArgumentNotValidException) e);
		}
		return toErrors(e);
	}
}
