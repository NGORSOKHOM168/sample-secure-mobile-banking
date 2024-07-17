package com.learning.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name="roles")
public class Role {	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
	private Long id;
	
	@Column(unique = true)	
	private String name;	

	@OneToMany(mappedBy = "role")
	private List<UserRole> userRole;
	
	@ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

}
