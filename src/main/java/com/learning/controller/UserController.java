package com.learning.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.UserAccountSignup;
import com.learning.dto.ResponeBase;
import com.learning.dto.UserAccountLogin;
import com.learning.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
	
	private final UserService userService;

	@PostMapping("/signup")
	public ResponeBase<?> signup(@Validated @RequestBody UserAccountSignup request){
		return userService.signup(request);
	}
	
	@PostMapping("/login")
	public ResponeBase<?> login(@Validated @RequestBody UserAccountLogin request){
		return userService.login(request);
	}
}
