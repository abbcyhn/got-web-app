package com.whalar.gameofthrones.handler;

import com.whalar.gameofthrones.contract.BaseResponse;
import com.whalar.gameofthrones.exception.ExceptionHandlingContext;
import com.whalar.gameofthrones.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

	private final ExceptionHandlingContext exceptionHandlingContext;

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public <T> ResponseEntity<BaseResponse<T>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return toErrorResponse(ex);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UnsupportedOperationException.class)
	public <T> ResponseEntity<BaseResponse<T>> handleUnsupportedOperationException(UnsupportedOperationException ex) {
		return toErrorResponse(ex);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public <T> ResponseEntity<BaseResponse<T>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return toErrorResponse(ex);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public <T> ResponseEntity<BaseResponse<T>> handleOtherExceptions(Exception ex) {
		return toErrorResponse(ex);
	}

	private <T> ResponseEntity<BaseResponse<T>> toErrorResponse(Exception e) {
		log.error(e.getMessage(), e);
		var strategy = exceptionHandlingContext.getStrategy(e);
		var status = strategy.getStatus();
		var errors = strategy.getErrors(e);
		return ResponseEntity.status(status).body(new BaseResponse<>(errors));
	}
}
