package com.learning.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {	
	 	
	    @Autowired 
	    private JwtTokenFilter jwtTokenFilter;
	    
	    @Autowired
	    private CustomUserDetailsService customUserDetailsService;
	    
	    @Autowired
	    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	    @Bean
	    AuthenticationProvider authenticationProvider(){
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(customUserDetailsService);
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }

	    @Bean
	    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
	        return config.getAuthenticationManager();
	    }

	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	http.csrf(csrf->csrf.disable());	
	    	http.authorizeHttpRequests(ar-> ar.requestMatchers("/api/v1/user/login","/api/v1/user/signup").permitAll());	    	
	    	http.authorizeHttpRequests(ar-> ar.anyRequest().authenticated());	    	
	    	http.httpBasic(Customizer.withDefaults());	    	
	    	http.exceptionHandling(ex->ex.authenticationEntryPoint(jwtAuthenticationEntryPoint));	    	
	    	http.sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));	    	
	    	http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);		    	
	        return http.build();
	    }
	    
	    @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    	    
}
