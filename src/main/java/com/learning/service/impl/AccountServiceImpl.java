package com.learning.service.impl;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.learning.dto.AccountBalanceRequest;
import com.learning.dto.AccountBalanceRespone;
import com.learning.dto.EmailDetails;
import com.learning.dto.FundTransferRequest;
import com.learning.dto.ResponeBase;
import com.learning.entity.Account;
import com.learning.entity.Transaction;
import com.learning.repository.AccountRepository;
import com.learning.repository.TransactionRepository;
import com.learning.service.AccountService;
import com.learning.service.SendMailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
	
	private final AccountRepository accRepository;
	private final TransactionRepository txnRepository;
	private final SendMailService sendMailService;
	@Override
	public ResponeBase<?> balanceInquiry(AccountBalanceRequest accountInquiry) { 
		
		Account account = accRepository.findByAccNumber(Long.valueOf(accountInquiry.accNumber()));
		
		if(account==null){
			return ResponeBase.builder().isSuccessful(false).code(HttpStatus.BAD_REQUEST.value())
					.message("Not Found").timespamp(LocalDateTime.now())
					.payload(null).build();
		}		
		
		AccountBalanceRespone accRespone = AccountBalanceRespone.builder()
				.accType(account.getAccType())
				.accName(account.getUser().getFullName())
				.accNumber(account.getAccNumber())
				.aviableBalance(account.getAviableBalance())
				.accCcy(account.getAccCcy())
				.build();
		
		return ResponeBase.builder().isSuccessful(true).code(HttpStatus.OK.value())
				.message("Successful").timespamp(LocalDateTime.now())
				.payload(accRespone).build();
	}

	@Override
	public ResponeBase<?> transferFund(FundTransferRequest request) {
		
		// Validate debit account 
		Account accountDebit = accRepository.findByAccNumberAndAccCcy(request.fromAcc(), request.fromAccCcy());
		if(accountDebit == null) {
			return ResponeBase.builder().isSuccessful(false).code(HttpStatus.BAD_REQUEST.value())
					.message("Inavalid debit account and its ccy").timespamp(LocalDateTime.now())
					.payload(null).build();
		}
		
		// Validate credit account
		Account accountCredit = accRepository.findByAccNumberAndAccCcy(request.toAcc(), request.toAccCcy());	
		if(accountCredit == null) {
			return ResponeBase.builder().isSuccessful(false).code(HttpStatus.BAD_REQUEST.value())
					.message("Inavalid credit account and its ccy").timespamp(LocalDateTime.now())
					.payload(null).build();
		}
		
		// validate balance
		Long debitAccBalane = Long.valueOf(accountDebit.getAviableBalance().intValue());
		Long tfAmount = Long.valueOf(request.tfAmount().intValue());
		if(debitAccBalane < tfAmount) {
			return ResponeBase.builder().isSuccessful(false).code(51)
					.message("Insufficient Balance").timespamp(LocalDateTime.now())
					.payload(null).build();
		}

		// debit amount from debit account
		accountDebit.setAviableBalance(accountDebit.getAviableBalance().subtract(request.tfAmount()));
		accRepository.save(accountDebit);		
		// save debit txn
		Transaction txnDebit = Transaction.builder().txnType("DEBIT").tfCcy(request.tfCcy())
				.tfAmount(request.tfAmount()).accNumber(accountDebit.getAccNumber())
				.ccy(accountDebit.getAccCcy()).purpose("Support my parents")
				.longNarative("Tranfer to [Account Number :"+accountCredit.getAccNumber()
				              +" with (Transfer Amount:" +request.tfCcy() +" "+ request.tfAmount()+")]")
				.status("Successful")
				.build();
		txnRepository.save(txnDebit);	
		
		// Must send email to dibit customer
		EmailDetails sendEmailToDebit = EmailDetails.builder()
					.recipient("sokhomngor202401@gmail.com")
					.subject("DEBIT TXN")
					.messageBody("Contratulation! you have transfered money successful"
					        +"\n To Account : " + accountDebit.getAccNumber()
					        +"\n Transfer Amount : "+request.fromAccCcy()+" " + request.tfAmount()
						   )
					.build();
		sendMailService.sendMail(sendEmailToDebit);

		// credit amount to account
		accountCredit.setAviableBalance(accountCredit.getAviableBalance().add(request.tfAmount()));
		accRepository.save(accountCredit);
		
		// save credit txn
		Transaction txnCredit = Transaction.builder().txnType("CREDIT").tfCcy(request.tfCcy())
				.tfAmount(request.tfAmount()).accNumber(accountCredit.getAccNumber())
				.ccy(accountDebit.getAccCcy()).purpose("Support my parents")
				.longNarative("Received from [Account Number :"+accountDebit.getAccNumber()
				              +" with (Transfer Amount:" +request.tfCcy() +" "+ request.tfAmount()+")]")
				.status("Successful")
				.build();
		txnRepository.save(txnCredit);
		
		// Must send email to credit customer
		EmailDetails sendEmailTCredit = EmailDetails.builder()
					.recipient("sokhomngor202401@gmail.com")
					.subject("CREDIT TXN")
					.messageBody("Contratulation! you have received money"
					        +"\n From Account : " + accountDebit.getAccNumber()
					        +"\n Received Amount : "+request.fromAccCcy()+" " + request.tfAmount()
						   )
					.build();
		sendMailService.sendMail(sendEmailTCredit);		
		
		return ResponeBase.builder().isSuccessful(true).code(HttpStatus.OK.value())
				.message("Contratulation! your transfer has bean successfully.").timespamp(LocalDateTime.now())
				.payload(null).build();
	}

}
