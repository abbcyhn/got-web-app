package com.whalar.gameofthrones.config;

import com.whalar.gameofthrones.exception.ExceptionHandlingContext;
import com.whalar.gameofthrones.exception.MethodArgumentNotValidExceptionStrategy;
import com.whalar.gameofthrones.exception.ResourceNotFoundException;
import com.whalar.gameofthrones.exception.ResourceNotFoundExceptionStrategy;
import com.whalar.gameofthrones.exception.UnsupportedOperationExceptionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.MethodArgumentNotValidException;


@Configuration
public class AppConfig {

	@Bean
	public ExceptionHandlingContext exceptionHandlingContext() {
		ExceptionHandlingContext context = new ExceptionHandlingContext();
		context.addStrategy(MethodArgumentNotValidException.class,
			new MethodArgumentNotValidExceptionStrategy());
		context.addStrategy(ResourceNotFoundException.class, new ResourceNotFoundExceptionStrategy());
		context.addStrategy(UnsupportedOperationException.class, new UnsupportedOperationExceptionStrategy());
		return context;
	}
}
