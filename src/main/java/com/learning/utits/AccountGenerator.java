package com.learning.utits;

import java.util.Random;

import com.learning.repository.AccountRepository;

public class AccountGenerator {

    private final AccountRepository accountRepository;
    private final Random random;

    public AccountGenerator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.random = new Random();
    }

    // Generate AccountNumber	
    private static final long MAX_ACCOUNT_NUMBER = 999999999999L;
    private static final long MIN_ACCOUNT_NUMBER = 100000000000L;
    public Long generateAccountNumber() {
        long accountNumber;
        do {
            accountNumber = generateRandomAccountNumber();
        } while (accountRepository.existsByAccNumber(accountNumber));
        return accountNumber;
    }
    private long generateRandomAccountNumber() {
        return MIN_ACCOUNT_NUMBER + (long) (random.nextDouble() * (MAX_ACCOUNT_NUMBER - MIN_ACCOUNT_NUMBER));
    }
}