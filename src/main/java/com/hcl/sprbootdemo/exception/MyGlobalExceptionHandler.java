package com.hcl.sprbootdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyGlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException ex){
		String message = ex.getMessage();
		APIResponse apiResponse = new APIResponse(message,false);
		return new ResponseEntity<APIResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(APIException.class)
	public ResponseEntity<APIResponse> myAPIException(APIException ex){
		String message = ex.getMessage();
		APIResponse apiResponse = new APIResponse(message,false);
		return new ResponseEntity<APIResponse>(apiResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<APIResponse> myResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
		String message = ex.getMessage();
		APIResponse apiResponse = new APIResponse(message,false);
		return new ResponseEntity<APIResponse>(apiResponse,HttpStatus.CONFLICT);
		
	}
}
