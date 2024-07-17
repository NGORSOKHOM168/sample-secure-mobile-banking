package com.learning.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Table(name = "transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String txnType;
	private String tfCcy;
	private BigDecimal tfAmount;
	private String ccy;
	private Long accNumber;
	private String status;	
	@Column(length = 500)
	private String longNarative;
	private String purpose;	
	private String chanel;
	@CreationTimestamp
	private LocalDate createdAt;
	
	@ManyToOne
	@JsonBackReference
	@Cascade(CascadeType.ALL)
	Account acc;

}
