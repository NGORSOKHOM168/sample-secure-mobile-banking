package com.learning.exeption;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.learning.dto.ResponeBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestControllerAdvice
public class ValidationExceptionHandler {
	
    private List<FieldError> fieldErrors;
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponeBase<?> handleValidationException(MethodArgumentNotValidException ex) {
	    fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
	    ValidationException validationException  = ValidationException.builder().fieldErrors(fieldErrors).build(); 
		return ResponeBase.builder()
				.isSuccessful(false)
				.code(HttpStatus.BAD_REQUEST.value())
				.message("Validation Error")
				.timespamp(LocalDateTime.now())
				.payload(validationException.getFieldErrors())
				.build();
    }

    @ExceptionHandler(ValidationException.class)
    public ResponeBase<?> handleValidationException(ValidationException ex) {
    	ValidationException validationException = ValidationException.builder().fieldErrors(fieldErrors).build(); 
 		return ResponeBase.builder()
 				.isSuccessful(false)
 				.code(HttpStatus.BAD_REQUEST.value())
 				.message("Validation Error")
 				.timespamp(LocalDateTime.now())
 				.payload(validationException.getFieldErrors())
 				.build();
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponeBase<?> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        return ResponeBase.builder()
 				.isSuccessful(false)
 				.code(HttpStatus.BAD_REQUEST.value())
 				.message("Bad request")
 				.timespamp(LocalDateTime.now())
 				.payload(ex.getMessage())
 				.build();
    }
    
}