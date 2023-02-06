package com.banking.service;

import java.util.List;

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
	
	public Account findAccountByEmail(String email) {
		return accountRepository.findByEmail(email);
	}
	
	public void deleteAccount(int id) {
		accountRepository.deleteById(id);
	}
	
	public List<Account> findAllAccount(){
		return accountRepository.findAll();
	}
	
	public Account findAccountById(int id) {
		return accountRepository.findById(id);
	}
	
	public List<Account> findAllAccountIsNotActive(boolean isActive) {
		return accountRepository.findByStatus(isActive);
	}
}
