package com.learning.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.AccountBalanceRequest;
import com.learning.dto.AccountStamentRequest;
import com.learning.dto.FundTransferRequest;
import com.learning.dto.ResponeBase;
import com.learning.service.AccountService;
import com.learning.service.AccountStatementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {
	
	private final AccountService accService;
	private final AccountStatementService accStatementService;

	@PostMapping("/balanceInquiry")
	public ResponeBase<?> balanceInquiry(@Validated @RequestBody AccountBalanceRequest request){
		return accService.balanceInquiry(request);
	}
	
	@PostMapping("/fundTransfer")
	public ResponeBase<?> transferFund(@Validated @RequestBody FundTransferRequest request){
		return accService.transferFund(request);
	}
	
	@GetMapping("/statement")
	public ResponeBase<?> requestStatement(@Validated @RequestBody AccountStamentRequest request){
		return accStatementService.AccountStatement(request);
	}
}
