package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	Admin findById(int id);
	Admin findByEmail(String email);
	
}
