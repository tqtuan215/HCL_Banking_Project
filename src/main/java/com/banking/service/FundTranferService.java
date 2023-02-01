package com.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.banking.entity.Transaction;
import com.banking.repository.FundTranferRepository;

@Service
public class FundTranferService {
	@Autowired
	private FundTranferRepository ftRepo;
	
	public Transaction addNewPayee(Transaction t) {
		// only contain accName and accNumber
		return ftRepo.save(t);
	}

	public Transaction findByTransId(int id) {
		return ftRepo.findById(id).orElse(null);
	}
}
