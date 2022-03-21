package com.product.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.product.model.GenericResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handleRunTimeException(RuntimeException e) {
        return error(INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler({CustomEntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(CustomEntityNotFoundException e) {
        return error(NOT_FOUND, e);
    }
    
    @ExceptionHandler({CustomInternalServerError.class})
    public ResponseEntity<?> handleInternalServerException(CustomInternalServerError e) {
        return error(INTERNAL_SERVER_ERROR, e);
    }
    
    private ResponseEntity<?> error(HttpStatus status, Exception e) {
       log.error("Exception : ", e);
        return ResponseEntity.status(status).body(new GenericResponse(status.value(), e.getMessage(), false));
    }

}
