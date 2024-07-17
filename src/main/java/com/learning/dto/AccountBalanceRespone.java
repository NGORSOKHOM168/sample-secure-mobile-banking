package com.learning.dto;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record AccountBalanceRespone(
		String accType,
		String accName,
		Long accNumber,
		String accCcy,
		BigDecimal aviableBalance
		) {
   
}
