package com.learning.dto;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record FundTransferRequest(
		Long fromAcc, 
		String fromAccCcy,
		Long toAcc,
		String toAccCcy,
		BigDecimal tfAmount,
		String tfCcy		
		) {

}
