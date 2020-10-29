package com.employeepayrolljdbc;

import java.time.LocalDate;

public class EmployeePayrollData {

	private int empId;
	private String empName;
	private double salary;
	private LocalDate startDate;
	private int compId;

	public EmployeePayrollData(int empId, String empName, int compId) {
		this.empName = empName;
		this.empId = empId;
		this.compId = compId;
	}

	public EmployeePayrollData(int id, String name, int compId, LocalDate startDate) {
		this.empName = name;
		this.empId = id;
		this.compId = compId;
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "Id: " + empId + " Name: " + empName + " Company Id: " + compId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	
	
}
