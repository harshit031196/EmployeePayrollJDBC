package com.employeepayrolljdbc;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Test;

public class PayrollTest {

	@Test
	public void matchEmployeeCount() throws SQLException {
		
		System.out.println(DBDemo.readData());
		assertEquals(3,DBDemo.readData().size());
		
	}
}
