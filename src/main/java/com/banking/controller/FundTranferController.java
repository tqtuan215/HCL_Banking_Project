package com.banking.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.banking.entity.Account;
import com.banking.entity.Transaction;
import com.banking.service.AccountService;
import com.banking.service.FundTranferService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fundTransfer")
public class FundTranferController {
	@Autowired
	private FundTranferService fundService;
	@Autowired
	private AccountService accService;
	
	@PostMapping("/addPayee")
	public ResponseEntity<?> addPayee(@RequestBody Transaction t, HttpServletRequest request) {		
		//check active
		String curentAccNumber = (String) request.getSession().getAttribute("account_number");
		Account sender = accService.findAccountByAccountNumber(curentAccNumber);		
		if(!sender.isStatus()) return new ResponseEntity<String>("Your acccount is not acctive. Please contact the bank serive",HttpStatus.NOT_ACCEPTABLE);
		// get in4
		String name = t.getAccName();
		String number = t.getAccNumber();
		// initialize session
		request.getSession().setAttribute("accName", name);
		request.getSession().setAttribute("accNumber", number);
		
		Account payee = accService.findAccountByAccountNumber(t.getAccNumber()); // find payee
		if(payee==null)
			return new ResponseEntity<String>("Payee is not found. Please check payee number account",HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<String>("Add payee successfully", HttpStatus.OK);
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<?> tranferT(Model model, HttpServletRequest rq, @RequestBody Transaction trans) {
		// get session in4
		String name = (String) rq.getSession().getAttribute("accName");
		String payeeAccNumber = (String) rq.getSession().getAttribute("accNumber");
		String curentAccNumber = (String) rq.getSession().getAttribute("account_number");
		// check session login
		if(curentAccNumber==null) return new ResponseEntity<String>("Please log in before transfer", HttpStatus.NOT_ACCEPTABLE);
		//handle transfer
		Account sender = accService.findAccountByAccountNumber(curentAccNumber); // find current account
		Account payee = accService.findAccountByAccountNumber(payeeAccNumber); // find payee
		
//		model.addAttribute("accName", name);
//		model.addAttribute("accNumber", number);
		
		// check whether balance is enough
		if(sender.getBalance() >= trans.getMoney()) {
			sender.setBalance(sender.getBalance()-trans.getMoney());
			payee.setBalance(payee.getBalance()+trans.getMoney());
			// sender's transaction 
			Transaction tSender = new Transaction();		
			tSender.setAccName(name);
			tSender.setAccNumber(payeeAccNumber);
			tSender.setMode(trans.getMode());				
			tSender.setDate(new Date());		
			tSender.setMoney(trans.getMoney());
			tSender.setType(true);		// true = outgoing
			tSender.setUserId(sender.getId());
			// payee's transaction
			Transaction tPayee = new Transaction();
			tPayee.setAccName(name);
			tPayee.setAccNumber(payeeAccNumber);
			tPayee.setMode(trans.getMode());				
			tPayee.setDate(new Date());		
			tPayee.setMoney(trans.getMoney());
			tPayee.setType(false);
			tPayee.setUserId(payee.getId());
			// add transaction both sender and payee
			fundService.addNewPayee(tSender);
			fundService.addNewPayee(tPayee);
		}			
		else
			return new ResponseEntity<String>("Balance is not enough",HttpStatus.NOT_ACCEPTABLE);
		return new ResponseEntity<String>("Transfer successfully!",HttpStatus.OK);
		
	}
//	@PostMapping("/addPayee")
//	public ModelAndView addPayee(@RequestBody Transaction t, HttpServletRequest request) {
//		// get in4
//		String name = t.getAccName();
//		long number = t.getAccNumber();
//		// initialize session
//		request.getSession().setAttribute("accName", name);
//		request.getSession().setAttribute("accNumber", number);
//		return new ModelAndView("redirect:/fundTransfer");
//	}
//	redirected ModelAndView
//	@PostMapping("/transfer")
//	public ModelAndView tranferT(Model model, HttpServletRequest rq, @RequestBody Transaction trans) {
//		String name = (String) rq.getSession().getAttribute("accName");
//		long number = (long) rq.getSession().getAttribute("accNumber");
//		String accNumber = (String) rq.getSession().getAttribute("account_number"); 
//		int userId = accService.findAccountByAccountNumber(accNumber).getId();
//		if(accNumber==null) return new ModelAndView("redirect:/login");
//		model.addAttribute("accName", name);
//		model.addAttribute("accNumber", number);
//		Transaction t = new Transaction();
//		t.setAccName(name);
//		t.setAccNumber(number);
//		t.setMode(trans.getMode());				
//		t.setDate(new Date());		
//		t.setMoney(trans.getMoney());
//		t.setUserId(userId);
//		fundService.addNewPayee(t);
//		rq.getSession().invalidate();
//		return new ModelAndView("redirect:/fundTransfer");
//		//return t.toString();
//		
//	}



}
