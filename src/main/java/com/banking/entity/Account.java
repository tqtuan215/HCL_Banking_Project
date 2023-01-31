package com.banking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String accountNumber;
	private String password;
	private String transactionPassword;
	private String email;
	private boolean internetBanking;
	private Integer attemp = 0;
	private String otp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTransactionPassword() {
		return transactionPassword;
	}

	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAttemp() {
		return attemp;
	}

	public void setAttemp(Integer attemp) {
		this.attemp = attemp;
	}

	public boolean isInternetBanking() {
		return internetBanking;
	}

	public void setInternetBanking(boolean internetBanking) {
		this.internetBanking = internetBanking;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

}
