package com.marcelo.tools.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

	/**
	 * NotFound error handler
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = {NotFoundError.class})
	public ResponseEntity<ErrorMessage> notFoundError(NotFoundError e) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setDeveloperMessage(e.getMessage());
		errorMessage.setUserMessage(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}
	
	/**
	 * Default error handler
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<ErrorMessage> exceptionError(Exception e) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setDeveloperMessage(e.getMessage());
		errorMessage.setUserMessage(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	}
	
	/**
	 * Handler for empty fields
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<ErrorMessage> validadeError (MethodArgumentNotValidException e) {
		List<FieldError> errors = e.getBindingResult().getFieldErrors();
		List<String> errorMsg = new ArrayList<>();
		
		for(FieldError err: errors) {
			errorMsg.add(String.format("[%s] - %s", err.getField(), "cannot be empty/null"));
		}
		
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setDeveloperMessage(String.join(", ", errorMsg));
		errorMessage.setDeveloperMessage(String.join(", ", errorMsg));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}
	
}
