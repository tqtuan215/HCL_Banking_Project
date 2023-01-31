package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Integer>{
    
	Account findByAccountNumber(String accountNumber);
	Account findByEmail(String email);
}
