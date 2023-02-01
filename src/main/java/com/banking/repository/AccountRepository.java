package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Integer>{
    
	Account findByAccountNumber(String accountNumber);
	Account findByEmail(String email);
	Account findById(int id);
	Account findByStatus(boolean isActive);
}
