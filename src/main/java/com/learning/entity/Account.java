package com.learning.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {	
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String accType;
	@Column(unique = true)
	private Long accNumber;
	private BigDecimal aviableBalance;
	private String accCcy;
	private Boolean isActive;
	private Boolean isClossed;
	private String createdBy;
	@CreationTimestamp
	private LocalDateTime createdAt;
	private String updatedBy;
	@UpdateTimestamp
	private LocalDateTime updatedAt;	

	@OneToMany(mappedBy="acc")
	List<Transaction> txn;

	@ManyToOne
	@JsonBackReference
    private User user;

}
