package com.whalar.gameofthrones.exception;

import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlingContext {
	private static final Map<Class<? extends Exception>, ExceptionHandlingStrategy> strategies = new HashMap<>();

	public void addStrategy(Class<? extends Exception> exceptionClass, ExceptionHandlingStrategy strategy) {
		strategies.put(exceptionClass, strategy);
	}

	public ExceptionHandlingStrategy getStrategy(Exception e) {
		return strategies.getOrDefault(e.getClass(), new DefaultExceptionStrategy());
	}
}
