package com.learning.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserAccountSignup(
		
	    @NotBlank(message = "FullName is required")
	    @Size(max = 25, message = "FullName must be max 25 characters")
		String fullName,
		
	    @NotBlank(message = "Gender is required")
	    @Size(max = 7, message = "Gender must be max 7 characters")
		String gender,
		
	    @NotBlank(message = "Phone is required")
	    @Size(max = 10, message = "Phone must be between 9 and 10 characters")
		String phone,	

	    @NotBlank(message = "Email is required")
	    @Email(message = "Email is not valid")
		String email,		

	    @NotBlank(message = "Address is required")
	    @Size(max = 255, message = "Address must be max 255 characters")
		String address,
		
	    @NotBlank(message = "Address is required")
	    @Size(max = 25, message = "Address must be max 25 characters")
		String username,	
		
	    @NotBlank(message = "Password is required")
	    @Size(min = 6, max = 12, message = "Password must be 6 and 12 characters")
		String password
		) {

}
