package com.whalar.gameofthrones.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
