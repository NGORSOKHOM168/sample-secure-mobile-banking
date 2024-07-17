package com.learning.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserAccountLogin(
		
	    @NotBlank(message = "Username is required")
	    @Size(max = 25, message = "User is inavlid")
		String username,

	    @NotBlank(message = "Password is required")
	    @Size(min = 6, max = 12, message = "Password is inavlid")
		String password
		
		) {

}
