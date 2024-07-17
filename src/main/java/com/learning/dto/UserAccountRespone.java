package com.learning.dto;

import java.util.List;

import com.learning.entity.Account;

import lombok.Builder;

@Builder
public record UserAccountRespone(	
		String fullName,
		String gender,
		String phone,	
		String email,		
		String address,
		String username, 
		List<Account> accounts		
		) {

}
