package com.learning.dataInit;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.learning.config.JwtService;
import com.learning.entity.Account;
import com.learning.entity.Authority;
import com.learning.entity.Role;
import com.learning.entity.User;
import com.learning.entity.UserRole;
import com.learning.repository.AccountRepository;
import com.learning.repository.AuthorityRepository;
import com.learning.repository.RoleRepository;
import com.learning.repository.UserRepository;
import com.learning.repository.UserRoleRepository;
import com.learning.utits.AccountGenerator;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInit {
	
	private final AuthorityRepository authorityRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final AccountRepository accRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired 
	AuthenticationManager authenticationManager;
	
	@Autowired 
	JwtService jwtUtil;
	
	// Create authorities's records
	Authority userRead = Authority.builder().name("user:read").build();
	Authority userWrite = Authority.builder().name("user:write").build();
	Authority userUpdate = Authority.builder().name("user:update").build();
	Authority userDelete = Authority.builder().name("user:delete").build();
	//====================================================================
	Authority accountRead = Authority.builder().name("account:read").build();
	Authority accountWrite = Authority.builder().name("account:write").build();
	Authority accountUpdate = Authority.builder().name("account:update").build();
	Authority accountDelete = Authority.builder().name("account:delete").build();
	//===========================================================================
	Authority transactionRead = Authority.builder().name("transaction:read").build();
	Authority transactionWrite = Authority.builder().name("transaction:write").build();
	Authority transactionUpdate = Authority.builder().name("transaction:Update").build();
	Authority transactionDelete = Authority.builder().name("transaction:delete").build();
	// End authorities ==================================================================
	
	// Create roles's records
	Role roleAdmin = Role.builder()
		.name("ADMIN")
		.authorities(List.of(
				userRead, userWrite, userUpdate, userDelete
				,accountRead, accountWrite, accountUpdate, accountDelete
				,transactionRead, transactionWrite, transactionUpdate, transactionDelete 
		))
		.build();
	
	Role roleManager = Role.builder()
		.name("MANAGER")
		.authorities(List.of(
				userRead, userUpdate, userDelete
				,accountRead, accountUpdate, accountDelete
				,transactionRead, transactionUpdate, transactionDelete 
		        ))
		.build();
	Role roleCustomer = Role.builder()
		.name("CUSTOMER")
		.authorities(List.of(
				userRead, userWrite, userUpdate
				,accountRead, accountWrite, accountUpdate
				,transactionRead, transactionWrite 
		        ))
		.build();
	// End roles 
	
	@PostConstruct
	public void init() {		
		User user = userRepository.findUserByUsername("SOKHOMNGOR");	
		if(user == null) {	
			
			// Insert data into table authorities
			List<Authority> authorities = List.of(
					userRead, userWrite, userUpdate, userDelete
					,accountRead, accountWrite, accountUpdate, accountDelete
					,transactionRead, transactionWrite, transactionUpdate, transactionDelete 
		    );
			authorityRepository.saveAll(authorities);
			
			// Insert data into table roles
			List<Role> roles = List.of(
					roleAdmin, roleManager, roleCustomer
	        );
			roleRepository.saveAll(roles);
			
			user = User.builder()
					.uuid(UUID.randomUUID().toString())
					.fullName("System Administrator")
					.gender("Male")
					.phone("0886883580")
					.email("admin@gmail.com")
					.address("Phnom Penh")
					.username("SOKHOMNGOR")
					.password(passwordEncoder.encode("123456"))
					.isActived(true)
					.isDisabled(false)
					.createdBy("SYSTEM")
					.build();
			
			userRepository.save(user);	

			// Insert user_roles
			UserRole userRoleAdmin = UserRole.builder()
					.user(user)
					.role(roleAdmin)
					.build();
			userRoleRepository.save(userRoleAdmin);		
			
			// save account
			AccountGenerator generatorAcc = new AccountGenerator(accRepository);
			Account accUsd = Account.builder()
					.accType("SAVING")
					.accNumber(generatorAcc.generateAccountNumber())
					.aviableBalance(BigDecimal.valueOf(10000))
					.accCcy("USD")
					.isActive(true)
					.isClossed(false)
					.createdBy("NGORSOKHOM")
					.user(user)
					.build();
			Account accKhr = Account.builder()
					.accType("SAVING")
					.accNumber(generatorAcc.generateAccountNumber())
					.aviableBalance(BigDecimal.valueOf(40000000))
					.accCcy("KHR")
					.isActive(true)
					.isClossed(false)
					.createdBy("NGORSOKHOM")
					.user(user)
					.build();	
			List<Account> accounts = List.of(accUsd, accKhr);
			accRepository.saveAll(accounts);
			
		}
	}

}
