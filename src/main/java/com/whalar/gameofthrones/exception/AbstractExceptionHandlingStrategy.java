package com.whalar.gameofthrones.exception;

import com.whalar.gameofthrones.contract.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractExceptionHandlingStrategy implements ExceptionHandlingStrategy {

	protected List<ErrorResponse> toErrors(Exception ex) {
		return Collections.singletonList(new ErrorResponse("", ex.getMessage()));
	}

	protected List<ErrorResponse> toErrors(MethodArgumentNotValidException ex) {
		return ex.getFieldErrors().stream()
			.map(fieldError -> new ErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()))
			.sorted(Comparator.comparing(ErrorResponse::getCode))
			.collect(Collectors.toList());
	}
}