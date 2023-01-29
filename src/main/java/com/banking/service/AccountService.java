package com.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.entity.Account;
import com.banking.repository.AccountRepository;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;

	public Account saveAccount(Account account) {
		return accountRepository.save(account);
	}
	
	public Account findAccountByAccountNumber(String accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber);
	}
	
	
}
