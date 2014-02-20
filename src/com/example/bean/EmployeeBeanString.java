package com.example.bean;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class EmployeeBeanString {
	@PrimaryKey
	private String email;
	
	private String name;
	
	private String team;
	
	 
	private String empId;
	
	 
	private String noOfLeaves;
	
	 
	private String contact;
	
	 
	private String reportingTo;
	
	 
	private String employeeType;
	
	 
	private String cleaves;
	 
	private String pleaves;
	 
	private String sleaves;
	
	public String getCleaves() {
		return cleaves;
	}
	public void setCleaves(String cleaves) {
		this.cleaves = cleaves;
	}
	public String getPleaves() {
		return pleaves;
	}
	public void setPleaves(String pleaves) {
		this.pleaves = pleaves;
	}
	public String getSleaves() {
		return sleaves;
	}
	public void setSleaves(String sleaves) {
		this.sleaves = sleaves;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getNoOfLeaves() {
		return noOfLeaves;
	}
	public void setNoOfLeaves(String noOfLeaves) {
		this.noOfLeaves = noOfLeaves;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getReportingTo() {
		return reportingTo;
	}
	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	
	
}
