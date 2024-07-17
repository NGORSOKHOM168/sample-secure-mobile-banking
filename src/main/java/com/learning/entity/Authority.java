package com.learning.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name="authorities")
public class Authority {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
	private Long id;	

	@Column(unique = true)
	private String name;	

	@ManyToMany(mappedBy = "authorities")	
	private List<Role> role;
}
