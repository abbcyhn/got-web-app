package com.whalar.gameofthrones.exception;

import com.whalar.gameofthrones.contract.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface ExceptionHandlingStrategy {
	HttpStatus getStatus();

	List<ErrorResponse> getErrors(Exception e);
}