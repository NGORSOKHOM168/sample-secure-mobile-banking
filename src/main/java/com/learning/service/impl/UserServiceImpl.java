package com.learning.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learning.config.JwtService;
import com.learning.dto.EmailDetails;
import com.learning.dto.ResponeBase;
import com.learning.dto.UserAccountRespone;
import com.learning.dto.UserAccountLogin;
import com.learning.dto.UserAccountSignup;
import com.learning.entity.Account;
import com.learning.entity.Role;
import com.learning.entity.User;
import com.learning.entity.UserRole;
import com.learning.repository.AccountRepository;
import com.learning.repository.RoleRepository;
import com.learning.repository.UserRepository;
import com.learning.repository.UserRoleRepository;
import com.learning.service.SendMailService;
import com.learning.service.UserService;
import com.learning.utits.AccountGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final AccountRepository accRepository;
	private final RoleRepository roleRepository;
	private final SendMailService sendMailService;
	private final PasswordEncoder passwordEncoder;

	@Autowired 
	AuthenticationManager authenticationManager;
	
	@Autowired 
	JwtService jwtUtil;

	@Override
	public ResponeBase<?> signup(UserAccountSignup userSignup) {
		
		// Check user already existed
		User user = userRepository.findUserByUsername(userSignup.username());
		if(user != null) {
			return ResponeBase.builder().isSuccessful(false).code(HttpStatus.BAD_REQUEST.value())
					.message("User is token please choose other").timespamp(LocalDateTime.now())
					.payload(null).build();
		}
		
		// Map request to User
		User newUser = User.builder().uuid(UUID.randomUUID().toString())
				.fullName(userSignup.fullName()).gender(userSignup.gender()).phone(userSignup.phone())
				.email(userSignup.email()).address(userSignup.address()).username(userSignup.username())
				.password(passwordEncoder.encode(userSignup.password())).isActived(true).isDisabled(false)
				.createdBy("SYSTEM").build();		
		userRepository.save(newUser);
				
		// Insert user_roles
        Role roleCustomer = roleRepository.findByName("CUSTOMER");        
		UserRole userRoleAdmin = UserRole.builder().user(user).role(roleCustomer).build();
		userRoleRepository.save(userRoleAdmin);	
		
		// Generate Account Number
		AccountGenerator generatorAcc = new AccountGenerator(accRepository);
		user = userRepository.findUserByUsername(userSignup.username());
		
		Account accUsd = Account.builder().accType("SAVING")
				.accNumber(generatorAcc.generateAccountNumber()).aviableBalance(BigDecimal.valueOf(10000))
				.accCcy("USD").isActive(true).isClossed(false).createdBy("NGORSOKHOM").user(user).build();
		
		Account accKhr = Account.builder().accType("SAVING").accNumber(generatorAcc.generateAccountNumber())
				.aviableBalance(BigDecimal.valueOf(40000000)).accCcy("KHR").isActive(true)
				.isClossed(false).createdBy("NGORSOKHOM").user(user).build();	
		
		List<Account> accounts = List.of(accUsd, accKhr);
		accRepository.saveAll(accounts);		
		accounts = accRepository.findByUserId(user.getId());
		
		// Must send email to user for created successfully
		EmailDetails sendEmailToDebit = EmailDetails.builder()
					.recipient("sokhomngor202401@gmail.com")
					.subject("ACCOUNT CREATION")
					.messageBody("Contratulation! you have created accounts successful"
					        +"\n Account USD : " + accUsd.getAccNumber()
					        +"\n Account KHR : " + accKhr.getAccNumber()
						   )
					.build();
		
		sendMailService.sendMail(sendEmailToDebit);
		
		UserAccountRespone userAccountRespone = UserAccountRespone.builder()
				 .fullName(user.getFullName()).gender(user.getGender()).phone(user.getPhone())
				 .email(user.getEmail()).address(user.getAddress()).accounts(accounts).build();	
		
		return ResponeBase.builder().isSuccessful(true).code(HttpStatus.OK.value())
				.message("User has been created successful").timespamp(LocalDateTime.now())
				.payload(userAccountRespone).build();
	}

	@Override
	public ResponeBase<?> login(UserAccountLogin userLogin) {		
		log.info("Inside login, Username: {}", userLogin.username());		
		try {			
			
			Authentication authentication  = authenticationManager.authenticate(
					  	new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password())
					);

			if(authentication.isAuthenticated()) {
				var token = jwtUtil.generateAccessToken(userLogin.username());				
		        User user = userRepository.findUserByUsername(userLogin.username());
		        user.setAccessToken(token);
		        userRepository.save(user);			        
				return ResponeBase.builder().isSuccessful(true).code(HttpStatus.OK.value())
						.message("Request token successful").timespamp(LocalDateTime.now())
						.payload("Token: "+ token).build();
			}else {
				return ResponeBase.builder().isSuccessful(true).code(HttpStatus.BAD_REQUEST.value())
						.message("Invalid token").timespamp(LocalDateTime.now())
						.payload(null).build();
			}
			
		} catch (Exception e) {
			log.error("Inside signup {}", e.getMessage());
			return ResponeBase.builder().isSuccessful(true).code(HttpStatus.BAD_REQUEST.value())
					.message("Request token has bean failed").timespamp(LocalDateTime.now())
					.payload("Error Message: "+e.getMessage()).build();
		}
	}

}
