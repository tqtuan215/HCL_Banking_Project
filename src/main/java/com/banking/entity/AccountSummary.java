package com.banking.entity;

import java.util.List;

public class AccountSummary {
	private Account account;
	private List<Transaction> transaction;
	
	
	public AccountSummary(Account account, List<Transaction> transaction) {
		super();
		this.account = account;
		this.transaction = transaction;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public List<Transaction> getTransaction() {
		return transaction;
	}
	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}
	
	
	
	
}
