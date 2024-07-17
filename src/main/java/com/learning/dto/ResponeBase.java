package com.learning.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ResponeBase <T> (
		Boolean isSuccessful,
		Integer code,
		String message,
		LocalDateTime timespamp,
		T payload
		) {
}