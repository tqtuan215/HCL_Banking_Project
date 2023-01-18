package com.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.entity.Account;
import com.banking.repository.AccountRepository;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;

//	Account checkLogin(String username, String password) {
//		Account account = accountRepository.checkLogin(username, password);
//		return account;
//	}

	public Account saveAccount(Account account) {
		return accountRepository.save(account);

	}
}
