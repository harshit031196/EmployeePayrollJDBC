package com.employeepayrolljdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

import EmployeePayrollService.EmployeePayrollService;
import EmployeePayrollService.EmployeePayrollService.IOService;
import exception.DatabaseException;

public class PayrollTest {

	@Test
	public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
		EmployeePayrollData[] employeeArray = { new EmployeePayrollData(1, "Praket Parth", 1000000),
				new EmployeePayrollData(1, "Trisha Chaudhary", 950000),
				new EmployeePayrollData(1, "Vishal Gupta", 1100000) };
		ArrayList<EmployeePayrollData> empPayrollDataList = new ArrayList<EmployeePayrollData>();
		for (EmployeePayrollData employeeData : employeeArray) {
			empPayrollDataList.add(employeeData);
		}
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(empPayrollDataList);
		employeePayrollService.writeEmployeeData(IOService.FILE_IO);
		employeePayrollService.printData(IOService.FILE_IO);
		assertEquals(3, employeePayrollService.countEntries(IOService.FILE_IO));
	}
	
	@Test
	public void givenEmployeeDataOnAFile_WhenRead_ShouldMatchTheEntries() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeeData(IOService.FILE_IO);
		assertEquals(3, employeePayrollService.employeeDataSize());
	}

	@Test
	public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchTotalEntries() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeeData(IOService.DB_IO);
		assertEquals(3, employeePayrollService.employeeDataSize());
	}
	
	@Test
	public void givenNewSalaryForEmployee_WhenUpdated_ShouldUpdateInTheDatabaseRecord() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeeData(IOService.DB_IO);
		try {
			employeePayrollService.updateEmployeeSalary("Terisa", 3000000.0);
			boolean result = employeePayrollService.isEmployeePayrollInSyncWithDB("Terisa");
			assertTrue(result);
		} catch (DatabaseException e) {}
	}
	
	@Test
	public void givenDateRange_WhenRetrievedFromDB_ShouldMatchTotalCount() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeeData(IOService.DB_IO);
		LocalDate startDate = LocalDate.of(2018, 01, 01);
		LocalDate endDate = LocalDate.now();
		ArrayList<EmployeePayrollData> list = employeePayrollService.readEmployeePayrollDataForDateRange(IOService.DB_IO, startDate, endDate);
		assertEquals(3, list.size());
	}
}
