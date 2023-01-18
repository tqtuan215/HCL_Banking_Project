package com.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entity.Account;
import com.banking.service.AccountService;


@RestController
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/addAccount")
	public Account addStudent(@RequestBody Account account) {
		return accountService.saveAccount(account);
	}
}
