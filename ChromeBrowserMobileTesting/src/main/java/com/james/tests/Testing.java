package com.james.tests;

import java.util.List;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.james.api.basetest.BaseTest;
import com.james.api.util.database.mapper.Customer;

import pageObjects.LoginPage;

public class Testing extends BaseTest{

	Logger logger;
	
	public Testing() {
	
		logger = Logger.getLogger(Testing.class.getName());
	}
	
	@Test
	public void testing() {
		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.enterUsername("tomsmith");
		loginPage.enterPassword("SuperSecretPassword!");
		loginPage.clickLoginButton();
		takeScreenshot("testing");
	}
	
	@Test
	public void getAllCustomers() {
		 List<Customer> customerList = testObject.getSqlMappers().getCustomerMapper().getCustomerAllCustomers();
		    System.out.println("========================================");
		    System.out.println("Customer Count: " + customerList.size());
		    System.out.println("========================================");
		    for (Customer customer : customerList) {
		    	 System.out.println("customerId: " + customer.getId());
		  	   System.out.println("First Name: " + customer.getFirst_name());
		  	   System.out.println("Last Name: " + customer.getLast_name());
		  	   System.out.println("Email: " + customer.getEmail());
		  	 System.out.println("=========================");
			}
	}
}
