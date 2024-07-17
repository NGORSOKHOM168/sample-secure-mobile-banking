package com.learning.service;

import com.learning.dto.UserAccountSignup;
import com.learning.dto.ResponeBase;
import com.learning.dto.UserAccountLogin;

public interface UserService {

	public ResponeBase<?> signup(UserAccountSignup userSignup);
	public ResponeBase<?> login(UserAccountLogin userLogin);
	
}
