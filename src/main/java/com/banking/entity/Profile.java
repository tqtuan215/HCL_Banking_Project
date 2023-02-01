package com.banking.entity;

import java.sql.Date;

public class Profile {
	private int id;
	private Date DOB;
	private String residentalAddress;
	private String permanentAddress;
	private String occupational;
	private String fullName;
	private int user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDOB() {
		return DOB;
	}
	public void setDOB(Date dOB) {
		DOB = dOB;
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getUser() {
		return user;
	}
	public void setUserId(int user) {
		this.user = user;
	}
	
	
}
