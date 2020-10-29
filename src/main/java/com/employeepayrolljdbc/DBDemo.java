package com.employeepayrolljdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class DBDemo {
	
	public static List<EmployeePayrollData> readData() {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service";
		String user = "root";
		String password = "harshit";
		Connection connection;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded successfully!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Driver Missing!", e);
		}
		
		listDrivers(); 
		
		String sql = "select * from employee;";
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(jdbcURL, user, password);
			System.out.println("Connection successfully established!" + connection);
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				int id = result.getInt("emp_id");
				String name = result.getString("name");
				LocalDate startDate = result.getDate("start_date").toLocalDate();
				int compId = result.getInt("comp_id");
				employeePayrollList.add(new EmployeePayrollData(id, name, compId,startDate));
				}
			connection.close();
			return employeePayrollList;
		}catch(SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	private static void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while(driverList.hasMoreElements()) {
			Driver driver = driverList.nextElement();
			System.out.println("   "+driver.getClass().getName());
		}
		
	}
}
