package com.learning.service;

import com.learning.dto.AccountBalanceRequest;
import com.learning.dto.FundTransferRequest;
import com.learning.dto.ResponeBase;

public interface AccountService {
	
	public ResponeBase<?> balanceInquiry(AccountBalanceRequest request);
	public ResponeBase<?> transferFund(FundTransferRequest request);
	
	
}
