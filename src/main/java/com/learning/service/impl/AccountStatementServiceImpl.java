package com.learning.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.learning.dto.AccountStamentRequest;
import com.learning.dto.EmailDetails;
import com.learning.dto.ResponeBase;
import com.learning.entity.Account;
import com.learning.entity.Transaction;
import com.learning.repository.AccountRepository;
import com.learning.repository.TransactionRepository;
import com.learning.service.AccountStatementService;
import com.learning.service.SendMailService;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountStatementServiceImpl implements AccountStatementService{

	private static final String FILE = "D:\\ACCOUNT_STATMENT\\Statement.pdf";
	
	private final AccountRepository accRepository;
	private final TransactionRepository txnRepository;
	private final SendMailService sendMailService;
	
	@Override
	public ResponeBase<?> AccountStatement(AccountStamentRequest request) {
		
		LocalDate start = LocalDate.parse(request.startDate(), DateTimeFormatter.ISO_DATE);
		LocalDate end = (LocalDate.parse(request.endDate(), DateTimeFormatter.ISO_DATE));
		
		List<Transaction> transactions = txnRepository.findTransactionsByCreatedAtRangeAndAccNumber(start,end, request.accNumber());
		
		Account account = accRepository.findByAccNumber(request.accNumber());	
		
		this.generatePdfStatemet(transactions, account, request.startDate(), request.endDate());	

		return ResponeBase.builder().isSuccessful(true).code(HttpStatus.OK.value())
				.message(HttpStatus.OK.name()).timespamp(LocalDateTime.now())
				.payload(transactions).build();
	}

	private void generatePdfStatemet(List<Transaction> transactions, Account account, String startDate, String endDate) {
		
		try {
			
			Rectangle statementSize = new Rectangle(PageSize.A4);
			Document document = new Document(statementSize);
			log.info("Set page size of Document");
			OutputStream outputStream = new FileOutputStream(FILE);
			PdfWriter.getInstance(document, outputStream);
			document.open();
			
			// Bank's name and address
			PdfPTable bankInfoTable = new PdfPTable(1);
			PdfPCell bankName = new PdfPCell(new Phrase("Bank Account Statement"));
			bankName.setBorder(0);
			bankName.setBackgroundColor(BaseColor.BLUE);
			bankName.setPadding(20f);		
			PdfPCell bankAddress = new PdfPCell(new Phrase("Str.203, No.72, BeongKok Destrict, Phnom Penh City, Cambodia."));
			bankAddress.setBorder(0);
			bankInfoTable.addCell(bankName);
			bankInfoTable.addCell(bankAddress);
			
			//Bank statement of Account Info
			PdfPTable statementInfo = new PdfPTable(2);			
			PdfPCell froDate = new PdfPCell(new Phrase("From Date : " + startDate));
			froDate.setBorder(0);		
			
			PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
			statement.setBorder(0);
			
			PdfPCell toDate = new PdfPCell(new Phrase("To Date : " + endDate));
			toDate.setBorder(0);
			
			String customerName = account.getUser().getFullName();
			PdfPCell name = new PdfPCell(new Phrase("Customer Name : " + customerName));
			name.setBorder(0);			
			
			PdfPCell address = new PdfPCell(new Phrase("Customer Address : " + account.getUser().getAddress()));
			address.setBorder(0);

			statementInfo.addCell(froDate);
			statementInfo.addCell(statement);
			statementInfo.addCell(toDate);
			statementInfo.addCell(address);
			
			// Table Header 
			PdfPTable tansactionTable = new PdfPTable(4);	
			PdfPCell date = new PdfPCell(new Phrase("Tansaction Date"));
			date.setBorder(0);date.setBackgroundColor(BaseColor.BLUE);

			PdfPCell accountType = new PdfPCell(new Phrase("Account Type"));
			accountType.setBorder(0);accountType.setBackgroundColor(BaseColor.BLUE);
			
			PdfPCell accountNumber = new PdfPCell(new Phrase("Account Number"));
			accountNumber.setBorder(0);accountNumber.setBackgroundColor(BaseColor.BLUE);
			
			PdfPCell status = new PdfPCell(new Phrase("Status"));
			status.setBorder(0);status.setBackgroundColor(BaseColor.BLUE);
			
			tansactionTable.addCell(date);
			tansactionTable.addCell(accountType);
			tansactionTable.addCell(accountNumber);
			tansactionTable.addCell(status);
			
			transactions.forEach(txn->{					
				tansactionTable.addCell(new Phrase(txn.getCreatedAt().toString()));	
				tansactionTable.addCell(new Phrase(txn.getTxnType().toString()));			
				tansactionTable.addCell(new Phrase(txn.getAccNumber().toString()));				
				tansactionTable.addCell(new Phrase(txn.getStatus().toString()));							
			});
			
			document.add(bankInfoTable);
			document.add(statementInfo);
			document.add(tansactionTable);
			document.close();	
			log.info("Account statement has been successfully generated.");
			
			// Must send email when successfully generated
			EmailDetails sendEmail = EmailDetails.builder()
					.recipient("sokhomngor202401@gmail.com")
					.subject("ACCOUNT STATEMEN")
					.messageBody("Kindly find your request account statment in attached file."
					        +"\n Your Account Details : " 
							+"\n Account Name : " + account.getUser().getFullName() 
					        +"\n Account Number : " + account.getAccNumber()
						   )
					.attachment(FILE)
					.build();
			sendMailService.sendMailWithAttachment(sendEmail);;
		
		} catch (FileNotFoundException e) {
			log.error("Failed export statment, Error Details :" + e.getMessage());
		} catch (DocumentException e) {
			log.error("Failed export statment, Error Details :" + e.getMessage());
		}
	}

}
