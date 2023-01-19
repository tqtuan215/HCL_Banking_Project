package com.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entity.Account;
import com.banking.service.AccountService;


@RestController
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/addAccount")
	public ResponseEntity<Account> addAccount(@RequestBody Account account) {
		Account checkAccount = accountService.findAccountByAccountNumber(account.getAccountNumber());
		if(checkAccount != null)
			return ResponseEntity.badRequest().build();
		return new ResponseEntity<Account>(accountService.saveAccount(account), HttpStatus.OK);
	}
	
	@GetMapping("/findAccount")
	public ResponseEntity<Account> getAccountByAccountNumber(@RequestParam String accountNumber){
		return new ResponseEntity<Account>(accountService.findAccountByAccountNumber(accountNumber), HttpStatus.OK); 
	}
}
