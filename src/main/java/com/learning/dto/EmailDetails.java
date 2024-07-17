package com.learning.dto;
import lombok.Builder;

@Builder
public record EmailDetails (
		String recipient,
		String messageBody,
		String subject,
		String attachment
		){
}
