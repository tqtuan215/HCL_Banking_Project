package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Integer>{
//    @Query("SELECT * FROM account a WHERE a.username = ?1 AND a.password = ?2")
//	Account checkLogin(String username, String password);
    
	Account findByAccountNumber(String accountNumber);
}
