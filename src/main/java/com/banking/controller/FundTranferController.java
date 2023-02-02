package com.banking.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.banking.entity.Account;
import com.banking.entity.Transaction;
import com.banking.service.AccountService;
import com.banking.service.FundTranferService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fundTransfer")
public class FundTranferController {
	@Autowired
	private FundTranferService fundService;
	@Autowired
	private AccountService accService;

	/**
	 * @param request check login and check account is active get session
	 *                account_number
	 * @return notification
	 */
	public ResponseEntity<?> check(HttpServletRequest request) {
		String senderNumber = (String) request.getSession().getAttribute("account_number");
		if (senderNumber == null)
			return new ResponseEntity<String>("Please log in before transfer", HttpStatus.NOT_ACCEPTABLE);

		// check active
		Account sender = accService.findAccountByAccountNumber(senderNumber); // find sender in4
		if (!sender.isStatus())
			return new ResponseEntity<String>("Your acccount is not acctive. Please contact the bank serive",
					HttpStatus.NOT_ACCEPTABLE);

		return null;
	}

	/**
	 * @param request 
	 * check session
	 * @return Map contains all in4 need to be transfer
	 */
	public ResponseEntity<?> checkTransfer(HttpServletRequest request) {
		String curentAccNumber = (String) request.getSession().getAttribute("account_number");
		String name = (String) request.getSession().getAttribute("payeeAccName");
		String payeeAccNumber = (String) request.getSession().getAttribute("payeeAccNumber");
		String money = (String) request.getSession().getAttribute("money");
		String mode = (String) request.getSession().getAttribute("mode");
		if (name.isEmpty() || payeeAccNumber.isEmpty() || money.isEmpty() || mode.isEmpty())
			return new ResponseEntity<String>("please fill all information", HttpStatus.NOT_ACCEPTABLE);
		Map<String, String> map = new HashMap<String, String>();
		map.put("payeeAccNumber", payeeAccNumber);
		map.put("senderAccNumber", curentAccNumber);
		map.put("payeeName", name);
		map.put("money", money);
		map.put("mode", mode);
		return new ResponseEntity<Map<?, ?>>(map, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<?> getSomeInf(HttpServletRequest request) {
		String curentAccNumber = (String) request.getSession().getAttribute("account_number");
		return new ResponseEntity<Account>(accService.findAccountByAccountNumber(curentAccNumber), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<?> fillFormTransfer(@RequestBody Transaction trans, HttpServletRequest request) {
		// get sender acc number
		String senderNumber = (String) request.getSession().getAttribute("account_number");

		// check session login
		if (senderNumber == null)
			return new ResponseEntity<String>("Please log in before transfer", HttpStatus.NOT_ACCEPTABLE);

		// check active
		Account sender = accService.findAccountByAccountNumber(senderNumber); // find sender in4
		if (!sender.isStatus())
			return new ResponseEntity<String>("Your acccount is not acctive. Please contact the bank serive",
					HttpStatus.NOT_ACCEPTABLE);

		// check exist payee
		Account payee = accService.findAccountByAccountNumber(trans.getAccNumber()); // find payee
		if (payee == null)
			return new ResponseEntity<String>("Payee is not found. Please check payee number account",
					HttpStatus.NOT_FOUND);

		// get payee's in4
		String payeeName = trans.getAccName();
		String payeeAccNumber = trans.getAccNumber();
		String mode = String.valueOf(trans.getMode());
		String money = String.valueOf(trans.getMoney());
		// initialize session
		request.getSession().setAttribute("payeeAccName", payeeName);
		request.getSession().setAttribute("payeeAccNumber", payeeAccNumber);
		request.getSession().setAttribute("money", money);
		request.getSession().setAttribute("mode", mode);

		// check balance
		if (sender.getBalance() >= trans.getMoney()) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		} else
			return new ResponseEntity<String>("Balance is not enough", HttpStatus.NOT_ACCEPTABLE);

	}

	@GetMapping("/confirm-transfer")
	public ResponseEntity<?> tranferT(HttpServletRequest rq) {

		// check session login
		String curentAccNumber = (String) rq.getSession().getAttribute("account_number");
		if (curentAccNumber == null)
			return new ResponseEntity<String>("Please log in before transfer", HttpStatus.NOT_ACCEPTABLE);

		return checkTransfer(rq);
	}

	@PostMapping("/confirm-transfer/transpassword")
	public ResponseEntity<?> lastStepTransfer(@RequestBody ObjectNode transPw, HttpServletRequest request) {
//	public String lastStepTransfer(@RequestBody ObjectNode transPw, HttpServletRequest request) {
		check(request);
		checkTransfer(request);
		
		// find sender in4
		String senderNumber = (String) request.getSession().getAttribute("account_number");
		Account sender = accService.findAccountByAccountNumber(senderNumber); 
		
		// get transaction password and check
		if (!sender.getTransactionPassword().equals(transPw.get("transPw").asText()))
//			return new ResponseEntity<String>("Invalid transaction password. Please try again",
//					HttpStatus.NOT_ACCEPTABLE);
			return new ResponseEntity<String>("test-"+transPw.get("transPw").asText(),
				HttpStatus.NOT_ACCEPTABLE);;


		// get session in4
		String name = (String) request.getSession().getAttribute("payeeAccName");
		String payeeAccNumber = (String) request.getSession().getAttribute("payeeAccNumber");
		long money = Long.parseLong((String) request.getSession().getAttribute("money"));
		int mode = Integer.parseInt((String) request.getSession().getAttribute("mode"));

		// check exist payee
		Account payee = accService.findAccountByAccountNumber(payeeAccNumber); // find payee
		sender.setBalance(sender.getBalance() - money);
		payee.setBalance(payee.getBalance() + money);
		// sender's transaction
		Transaction tSender = new Transaction();
		tSender.setAccName(name);
		tSender.setAccNumber(payeeAccNumber);
		tSender.setMode(mode);
		tSender.setDate(new Date());
		tSender.setMoney(money);
		tSender.setType(true); // true = outgoing
		// tSender.setAccount(sender);
		tSender.setUserId(sender.getId());
		// payee's transaction
		Transaction tPayee = new Transaction();
		tPayee.setAccName(name);
		tPayee.setAccNumber(payeeAccNumber);
		tPayee.setMode(mode);
		tPayee.setDate(new Date());
		tPayee.setMoney(mode);
		tPayee.setType(false);
		// tPayee.setAccount(payee);
		tPayee.setUserId(payee.getId());
		// add transaction both sender and payee
		fundService.addNewPayee(tSender);
		fundService.addNewPayee(tPayee);
		return new ResponseEntity<String>("Transfer successfully",HttpStatus.OK);
		//return "ok";
	}
}
