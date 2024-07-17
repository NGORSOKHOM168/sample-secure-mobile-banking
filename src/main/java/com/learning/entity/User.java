package com.learning.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
	private Long id;
	private String uuid;
	private String fullName;
	private String gender;
	
	@Column(unique = true)
	private String phone;
	
	@Column(unique = true)
	private String email;	
	
	private String address;	
	
	@Column(unique = true)
	private String username;
	
	private String password;
	private Boolean isActived;
	private Boolean isDisabled;	
	private String createdBy;
	private String accessToken;
	private Integer attempt;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	private String updatedBy;
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "user")
	private List<UserRole> userRole;

	@OneToMany(mappedBy = "user")
	List<Account> account;
	
}