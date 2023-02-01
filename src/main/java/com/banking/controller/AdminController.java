package com.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entity.Account;
import com.banking.entity.Admin;
import com.banking.service.AccountService;
import com.banking.service.AdminService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private AccountService accService;

	@PostMapping("/login")
	public ResponseEntity<String> checkAdmin(@RequestBody Admin data, HttpServletRequest rq) {

		Admin a = adminService.findAdminByEmail(data.getEmail());
		if (a == null)
			return new ResponseEntity<String>("Admin is not found", HttpStatus.NOT_FOUND);
		else if (!a.getPassword().equals(data.getPassword()))
			return new ResponseEntity<String>("Invalid password", HttpStatus.NOT_ACCEPTABLE);
		rq.getSession().setAttribute("admin", true);
		return new ResponseEntity<String>("Admin login", HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/dashboard")
	public ResponseEntity<?> showRequest(HttpServletRequest rq){
		boolean isAdmin = (boolean) rq.getSession().getAttribute("admin");
		if(!isAdmin) 
			return new ResponseEntity<String>("you are not admin",HttpStatus.NOT_ACCEPTABLE);
		return new ResponseEntity<List<Account>>((List<Account>) accService.findAllAccountIsNotActive(false),HttpStatus.OK);
	}

	@PutMapping("/dashboard")
	public ResponseEntity<?> approveRequest(@RequestBody Account acc) {
		Account a = accService.findAccountById(acc.getId());
		a.setStatus(true);		
		return new ResponseEntity<Account>(accService.saveAccount(a), HttpStatus.OK);
	}
	@DeleteMapping("/dashboard/cancel")
	public ResponseEntity<?> cancelRequest(@RequestBody Account acc) {		
		accService.deleteAccount(acc.getId());
		return new ResponseEntity<String>("Canceled request",HttpStatus.OK);
	}

}
