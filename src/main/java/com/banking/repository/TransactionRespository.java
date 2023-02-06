package com.banking.repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.Transaction;
public interface TransactionRespository extends JpaRepository<Transaction, Integer> {
	List<Transaction> findTransactionByuserId(Integer userid);
}
