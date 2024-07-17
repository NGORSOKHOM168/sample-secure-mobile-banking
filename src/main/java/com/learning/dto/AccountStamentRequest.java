package com.learning.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AccountStamentRequest(
		
		@NotNull(message = "Account Number is required")
		Long accNumber,
	    @NotBlank(message = "Start Date is required")
		String startDate,		
	    @NotBlank(message = "End Date is required")
		String endDate
		
		) {

}
