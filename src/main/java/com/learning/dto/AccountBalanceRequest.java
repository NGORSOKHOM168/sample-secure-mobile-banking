package com.learning.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AccountBalanceRequest(
		
	    @NotNull(message = "Account Number is required")
		String accNumber
		
		) {

}
