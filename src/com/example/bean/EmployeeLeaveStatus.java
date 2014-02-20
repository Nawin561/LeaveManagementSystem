package com.example.bean;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class EmployeeLeaveStatus {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String email;
	@Persistent
	private String balanceCL;
	@Persistent
	private String balancePL;
	@Persistent
	private String balanceSL;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBalanceCL() {
		return balanceCL;
	}

	public void setBalanceCL(String cleaves) {
		this.balanceCL = cleaves;
	}

	public String getBalancePL() {
		return balancePL;
	}

	public void setBalancePL(String balancePL) {
		this.balancePL = balancePL;
	}

	public String getBalanceSL() {
		return balanceSL;
	}

	public void setBalanceSL(String balanceSL) {
		this.balanceSL = balanceSL;
	}

}
