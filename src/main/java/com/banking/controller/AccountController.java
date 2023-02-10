package com.banking.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entity.Account;
import com.banking.service.AccountService;
import com.banking.service.EmailOTPService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private EmailOTPService emailService;

	@PostMapping("/addAccount")
	public ResponseEntity<?> addAccount(@RequestBody Account account) {
		Account accountNumber = accountService.findAccountByAccountNumber(account.getAccountNumber());
		Account accountEmail = accountService.findAccountByAccountNumber(account.getEmail());
		if (accountNumber != null || accountEmail != null)
			return new ResponseEntity<String>("Account exists", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Account>(accountService.saveAccount(account), HttpStatus.OK);
	}

	@GetMapping("/findAccount")
	public ResponseEntity<Account> getAccountByAccountNumber(@RequestParam String accountNumber) {
		return new ResponseEntity<Account>(accountService.findAccountByAccountNumber(accountNumber), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginAccount(@RequestBody Map<String, String> data, HttpServletRequest request) {
		String accountNumber = data.get("accountNumber");
		String password = data.get("password");
		Account checkAccount = accountService.findAccountByAccountNumber(accountNumber);
		if (checkAccount != null) {
			if(checkAccount.getAttemp() == 3)
				return new ResponseEntity<String>("Account Locked", HttpStatus.BAD_REQUEST);
			else {
				if (!checkAccount.getPassword().equals(password)) {
					checkAccount.setAttemp(checkAccount.getAttemp() + 1);
					return new ResponseEntity<String>("Wrong password", HttpStatus.BAD_REQUEST);
				}
				else {
					checkAccount.setAttemp(0);
					request.getSession().setAttribute("account_number", accountNumber);
					request.getSession().setAttribute("email", checkAccount.getEmail());
					request.getSession().setAttribute("id", checkAccount.getId());
					accountService.saveAccount(checkAccount);
					return new ResponseEntity<Account>(checkAccount, HttpStatus.OK);
				}
			}	
		}
		return new ResponseEntity<String>("Account not found", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		
		return "logout";
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> loginData(HttpServletRequest request){
		if(request.getSession().getAttribute("account_number") == null) {
			return new ResponseEntity<String>("No session", HttpStatus.BAD_REQUEST);
		}else
			return new ResponseEntity<Account>(accountService.findAccountByAccountNumber(request.getSession().getAttribute("account_number").toString()), HttpStatus.OK);
	}
	
	@PostMapping("/checkSession")
	public ResponseEntity<?> checkSession(HttpServletRequest request){
		if(request.getSession().getAttribute("account_number") != null) {
			return new ResponseEntity<String>("Already logging", HttpStatus.OK);
		}
		return new ResponseEntity<String>("No session", HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/internetBanking")
	public ResponseEntity<?> registerInternetBankingAccount(@RequestBody ObjectNode data, HttpServletRequest request) {
		if(request.getSession().getAttribute("account_number") == null) {
			return new ResponseEntity<String>("No session", HttpStatus.BAD_REQUEST);
		}else {
			String accountNumber = request.getSession().getAttribute("account_number").toString();
			String password = data.get("password").asText();
			String transactionPassword = data.get("transactionPassword").asText();
			Account checkAccount = accountService.findAccountByAccountNumber(accountNumber);
			if (checkAccount != null) {
				if (!checkAccount.getPassword().equals(password))
					return new ResponseEntity<String>("Wrong password", HttpStatus.BAD_REQUEST);
				else if (!checkAccount.getTransactionPassword().equals(transactionPassword))
					return new ResponseEntity<String>("Wrong transaction password", HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<String>("Account not found", HttpStatus.BAD_REQUEST);
			checkAccount.setInternetBanking(true);
			return new ResponseEntity<Account>(accountService.saveAccount(checkAccount), HttpStatus.OK);
		}
	}

	@PutMapping("/newPassword")
	public ResponseEntity<?> setNewPassword(@RequestBody ObjectNode data, HttpServletRequest request) {
		String accountNumber = null;
		if(request.getSession().getAttribute("account_number") == null) {
			accountNumber = data.get("accountNumber").asText();
		}else {
			accountNumber = request.getSession().getAttribute("account_number").toString();
		}
		String password = data.get("password").asText();
		String newPassword = data.get("newPassword").asText();
		Account checkAccount = accountService.findAccountByAccountNumber(accountNumber);
		if (!checkAccount.getPassword().equals(password))
			return new ResponseEntity<String>("Wrong password", HttpStatus.BAD_REQUEST);
		checkAccount.setPassword(newPassword);
		checkAccount.setAttemp(0);
		return new ResponseEntity<Account>(accountService.saveAccount(checkAccount), HttpStatus.OK);
	}
	
	@PostMapping("sendOTP")
	public ResponseEntity<?> sendOTP(HttpServletRequest request){
		if(request.getSession().getAttribute("account_number") == null) {
			return new ResponseEntity<String>("No session", HttpStatus.BAD_REQUEST);
		}else {
			String email = request.getSession().getAttribute("email").toString();
			Account checkAccount = accountService.findAccountByEmail(email);
			int randomPin = (int)(Math.random()*9000)+1000;
		    String otp = String.valueOf(randomPin);
			emailService.sendSimpleEmail(email, "Your OTP is: ", otp);
			checkAccount.setOtp(otp);
			accountService.saveAccount(checkAccount);
			return new ResponseEntity<String>("Send OTP success", HttpStatus.OK);
		}
	}
	
	@PostMapping("/forgotPassword")
	public ResponseEntity<?> forgotPassword(@RequestBody ObjectNode data) {
		String accountNumber = data.get("accountNumber").asText();
		String otp = data.get("otp").asText();
		Account checkAccount = accountService.findAccountByAccountNumber(accountNumber);
		if(!checkAccount.getOtp().equals(otp))
			return new ResponseEntity<String>("Wrong OTP", HttpStatus.BAD_REQUEST);
		checkAccount.setOtp(null);
		accountService.saveAccount(checkAccount);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	
	

}
