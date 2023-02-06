package com.banking.service;




import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.entity.Profile;
import com.banking.entity.Transaction;
import com.banking.entity.Account;
import com.banking.entity.AccountDetails;
import com.banking.entity.AccountStatement;
import com.banking.repository.AccountRepository;
import com.banking.repository.ProfileRespository;
import com.banking.repository.TransactionRespository;
@Service
public class DashBoardService {
	
	@Autowired
	private ProfileRespository profileRespository;
	
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionRespository transactionRespository;
	
	public Profile showProfileByID(Integer id) {
		return profileRespository.findProfileById(id);
	}
	
	public Account showAccountByID(Integer id) {
		return accountRepository.findAccountById(id);
	}
	
	public List<Transaction> showTransactionByID(Integer userid){
		return transactionRespository.findTransactionByuserId(userid);
	}
	
	public AccountDetails getDetails(Integer id) {
        Optional<Profile> optionalprofile = profileRespository.findById(id);
        Optional<Account> optionalaccount = accountRepository.findById(id);
        Profile profile = optionalprofile.get();
        Account account = optionalaccount.get();
        return mapToDtoDetail(profile, account);
    }

    private AccountDetails mapToDtoDetail(Profile profile,Account account) {
        return new AccountDetails(profile.getFullName(),account.getAccountNumber(),account.getEmail(),account.getIdNumber(), profile.getDOB(),profile.getResidentalAddress(),profile.getPermanentAddress(),profile.getOccupational());
    }
	
	public AccountStatement getStatement(Integer id) {
        Optional<Profile> optionalprofile = profileRespository.findById(id);
        Optional<Account> optionalaccount = accountRepository.findById(id);
        Profile profile = optionalprofile.get();
        Account account = optionalaccount.get();
        return mapToDtoStatement(profile, account);
    }

    private AccountStatement mapToDtoStatement(Profile profile,Account account) {
        return new AccountStatement(profile.getFullName(), account.getAccountNumber(), account.getAccType(), account.getBalance());
    }
    
    
    
    
}
