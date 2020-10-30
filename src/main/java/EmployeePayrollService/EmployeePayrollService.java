package EmployeePayrollService;

import java.util.ArrayList;
import java.util.Scanner;

import com.employeepayrolljdbc.EmployeePayrollDBService;
import com.employeepayrolljdbc.EmployeePayrollData;

import exception.DatabaseException;
import fileioservice.EmployeePayrollFileIOService;

public class EmployeePayrollService {
	
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private ArrayList<EmployeePayrollData> employeePayrollDataList;

	public EmployeePayrollService(ArrayList<EmployeePayrollData> employeePayrollDataList) {
		super();
		this.employeePayrollDataList = employeePayrollDataList;
	}

	public EmployeePayrollService() {
		this.employeePayrollDataList = new ArrayList<EmployeePayrollData>();
	}

	/**
	 * Reads from console, file, and DB
	 * @throws DatabaseException 
	 */
	public ArrayList<EmployeePayrollData> readEmployeeData(IOService ioService) {
		if (ioService.equals(IOService.CONSOLE_IO)) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the employee data\n\nEnter Employee name: ");
			String empName = sc.next();
			System.out.println("Enter the employee id:");
			int empId = sc.nextInt();
			System.out.println("Enter the employee salary:");
			double salary = sc.nextDouble();
			employeePayrollDataList.add(new EmployeePayrollData(empId, empName, salary));
			sc.close();
		} 
		if (ioService.equals(IOService.FILE_IO)) {
			this.employeePayrollDataList = new EmployeePayrollFileIOService().readEmployeePayrollData();
		}
		if(ioService.equals(IOService.DB_IO)) {
			try {
				this.employeePayrollDataList = new EmployeePayrollDBService().readEmployeeData();
			} catch (DatabaseException e) {
				System.out.println(e.getMessage());
			}
		}
		return employeePayrollDataList;
	}

	/**
	 * Writes to file or consoles
	 */
	public void writeEmployeeData(IOService ioService) {
		if (ioService.equals(IOService.CONSOLE_IO)) {
			employeePayrollDataList.forEach(System.out::println);
		} else if (ioService.equals(IOService.FILE_IO)) {
			new EmployeePayrollFileIOService().writeData(employeePayrollDataList);
		}

	}
	
	/**
	 * @param name
	 * @param salary
	 * @throws DatabaseException
	 * To Update Employee Salary
	 */
	public void updateEmployeeSalary(String name, double salary) throws DatabaseException {
		int result = new EmployeePayrollDBService().updateEmployeeSalary(name, salary);
		EmployeePayrollData employeePayrollData = getEmployeeData(name);
		if(employeePayrollData != null) employeePayrollData.setSalary(salary);
	}
	
	/**
	 * @param name
	 * @return T/F whether Payroll is in sync with DB or not
	 * @throws DatabaseException
	 */
	public boolean isEmployeePayrollInSyncWithDB(String name) throws DatabaseException {
		ArrayList<EmployeePayrollData> list = new EmployeePayrollDBService().getEmployeeData(name);
		return list.get(0).equals(getEmployeeData(name));
	}

	/**
	 * @param name
	 * @returns Employee Payroll Data Object with given employee name
	 */
	private EmployeePayrollData getEmployeeData(String name) {
		return employeePayrollDataList.stream()
									  .filter(employeePayrollData -> employeePayrollData.getEmpName().equals(name))
									  .findFirst()
									  .orElse(null);
	}

	/**
	 * Counts the entries
	 */
	public long countEntries(IOService ioService) {
		return new EmployeePayrollFileIOService().countEntries();
	}

	/**
	 * Prints employee Payroll Data
	 */
	public void printData(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO)) {
			new EmployeePayrollFileIOService().printEmployeePayrollData();
		} else if (ioService.equals(IOService.CONSOLE_IO)) {
			employeePayrollDataList.forEach(System.out::println);
		}
	}

	/**
	 *Returns the size of the employee payroll data list
	 */
	public long employeeDataSize() {
		return employeePayrollDataList.size();
	}
}
