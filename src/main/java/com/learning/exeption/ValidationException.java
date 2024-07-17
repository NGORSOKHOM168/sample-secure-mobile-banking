package com.learning.exeption;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;	
	private final List<FieldError> fieldErrors;
}