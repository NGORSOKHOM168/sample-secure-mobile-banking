package com.learning.utits;

public class MessageAndCodeUtils {
	
	public static final String ACCOUNT_STATUS_ACT              = "Active";
	public static final String ACCOUNT_STATUS_INA              = "Inactive";
	
	public static final String ACCOUNT_EXIST_CODE              = "001";
	public static final String ACCOUNT_EXIST_MESSAGE           = "This user already has been created!";

	public static final String ACCOUNT_CREATION_CODE           = "002";
	public static final String ACCOUNT_CREATION_MESSAGE        = "This user has been successfully created.";

	public static final String ACCOUNT_NOT_EXIST_CODE          = "003";
	public static final String ACCOUNT_NOT_EXIST_MESSAGE       = "User with the provided account does not exist!";	
	public static final String ACCOUNT_FOUND_CODE              = "004";
	public static final String ACCOUNT_FOUND_MESSAGE           = "User account found.";
	
	public static final String ACCOUNT_CREDIT_FAIL_CODE        = "005";
	public static final String ACCOUNT_CREDIT_FAIL_MESSAGE     = "Account was credited failure!";	
	public static final String ACCOUNT_CREDIT_SUCESS_CODE      = "006";
	public static final String ACCOUNT_CREDIT_SUCCESS_MESSAGE  = "Account has been credited successully.";	

	public static final String ACCOUNT_DEBIT_FAIL_CODE         = "007";
	public static final String ACCOUNT_DEBIT_FAIL_MESSAGE      = "Account was debited failure!";
	public static final String ACCOUNT_DEBIT_SUCCESS_CODE      = "008";
	public static final String ACCOUNT_DEBIT_SUCCESS_MESSAGE   = "Account was debited successful.";

	public static final String INSUFFICIENT_BALANCE_CODE       = "009";
	public static final String INSUFFICIENT_BALANCE_MESSAGE    = "Insufficient balance!";	

	public static final String ACCOUNT_TRANSFER_SUCCESS_CODE    = "010";
	public static final String ACCOUNT_TRANSFER_SUCCESS_MESSAGE = "Account was transfer amoount successfully.";
	
	public static final String ACCOUNT_TRANSFER_FAIL_CODE       = "011";
	public static final String ACCOUNT_TRANSFER_FAIL_MESSAGE    = "Account was transfer amoount failured!";
}