package com.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Integer>{
    Account findAccountById(Integer id);
	Account findByAccountNumber(String accountNumber);
	Account findByEmail(String email);
	Account findById(int id);
	List<Account> findByStatus(boolean isActive);
}
