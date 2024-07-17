package com.learning.service;

import com.learning.dto.AccountStamentRequest;
import com.learning.dto.ResponeBase;

public interface AccountStatementService {
	public ResponeBase<?> AccountStatement(AccountStamentRequest request);
}
