package com.banking.controller;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entity.AccountStatement;
import com.banking.entity.Account;
import com.banking.entity.AccountDetails;
import com.banking.entity.Profile;
import com.banking.entity.Transaction;
import com.banking.entity.AccountSummary;
import com.banking.service.DashBoardService;

@RestController
public class DashBoardController {
	@Autowired
	private DashBoardService dashBoardService;
	
//	@GetMapping("/AccountDetails/{id}")
//	public ResponseEntity<AccountDetails> getAccountDetailsByID(@PathVariable Integer id) {
//		Account account = dashBoardService.showAccountByID(id);
//	if (account == null) {
//	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	    }                                              
//		Profile profile = dashBoardService.ProfileByID;
//	AccountSummary accountSummary = new AccountSummary(account, profile);
//	return new  ResponseEntity<>(accountSummary, HttpStatus.OK);
//	}
	
	@GetMapping("/AccountDetails/{id}")
	public ResponseEntity<AccountDetails> getAccountDetailsByID(@PathVariable Integer id) {
		AccountDetails account = dashBoardService.getDetails(id);
		if (account == null) {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
		return new  ResponseEntity<>(account, HttpStatus.OK);
	}
	
	@GetMapping("/AccountSummary/{id}")
	public ResponseEntity<AccountSummary> getAccountSummaryByID(@PathVariable Integer id){
		Account account = dashBoardService.showAccountByID(id);
		if (account == null) {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }                                              
		List<Transaction> transaction = dashBoardService.showTransactionByID(id);
		AccountSummary accountSummary = new AccountSummary(account, transaction);
		return new  ResponseEntity<>(accountSummary, HttpStatus.OK);
	}
	
	@GetMapping("/AccountStatement/{id}")
	 public ResponseEntity<AccountStatement> getAccount(@PathVariable Integer id) {
		AccountStatement account = dashBoardService.getStatement(id);
	        if (account == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(account, HttpStatus.OK); 
	}
}
