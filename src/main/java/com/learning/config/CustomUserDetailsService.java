package com.learning.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.entity.User;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
    private com.learning.repository.UserRepository userRepository;
	

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
		{
		
        User user = userRepository.findUserByUsername(username);
        
        if(user==null) {
        	throw new UsernameNotFoundException("User not found: " + username);
        }
       
//        List<GrantedAuthority> authorities = List.of(user.getUserRole().get(0).getRole()).stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());

        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
        
    }
}