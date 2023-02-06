package com.banking.entity;


public class AccountStatement {
	private String name;
	private String accountNumber;
	private Integer accType;
	private long balance;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Integer getAccType() {
		return accType;
	}
	public void setAccType(Integer accType) {
		this.accType = accType;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public AccountStatement(String name, String accountNumber, Integer accType, long balance) {
		super();
		this.name = name;
		this.accountNumber = accountNumber;
		this.accType = accType;
		this.balance = balance;
	}
	
	
	
}
