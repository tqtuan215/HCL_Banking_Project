package com.banking.entity;

import java.sql.Date;

public class AccountDetails {
	private String fullName;
	private String phone;
	private String email;
	private String idNumber;
	private Date dob;
	private String residentalAddress;
	private String permanentAddress;
	private String occupational;
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getResidentalAddress() {
		return residentalAddress;
	}
	public void setResidentalAddress(String residentalAddress) {
		this.residentalAddress = residentalAddress;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public String getOccupational() {
		return occupational;
	}
	public void setOccupational(String occupational) {
		this.occupational = occupational;
	}
	public AccountDetails(String fullName, String phone, String email, String idNumber, Date dob,
			String residentalAddress, String permanentAddress, String occupational) {
		super();
		this.fullName = fullName;
		this.phone = phone;
		this.email = email;
		this.idNumber = idNumber;
		this.dob = dob;
		this.residentalAddress = residentalAddress;
		this.permanentAddress = permanentAddress;
		this.occupational = occupational;
	}
	
	
	
	
	
	
	
	
	
	
}
