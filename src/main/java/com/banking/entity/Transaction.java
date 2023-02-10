package com.banking.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
   

@Entity
public class Transaction {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transId;
	private String date;
	private long money;
	private boolean type;
	private Integer mode;
	private String accName;
	private String accNumber;	
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTransId() {
		return transId;
	}
	public void setTransId(Integer transId) {
		this.transId = transId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");		
		date = new Date();
		this.date = sdf.format(date);
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	public boolean getType() {
		return type;
	}
	public void setType(boolean type) {
		this.type = type;
	}
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}
	@Override
	public String toString() {
		return "Transaction [transId=" + transId + ", date=" + date + ", money=" + money + ", type=" + type + ", mode="
				+ mode + ", accName=" + accName + ", accNumber=" + accNumber + ", userId=" + userId + "]";
	}
	
	
}
