package com.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.banking.entity.Admin;
import com.banking.repository.AdminRepository;

@Service
public class AdminService {
	@Autowired
	private AdminRepository repo;
	
	public Admin findAdminById(int id) {
		return repo.findById(id);
	}
	public Admin findAdminByEmail(String email) {
		return repo.findByEmail(email);
	}

	
}
