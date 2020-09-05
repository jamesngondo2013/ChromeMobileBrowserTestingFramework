package com.james.api.util.database.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface CustomerMapper {

	@ResultType(value = Customer.class)
	@Results(id = "customerResultMap", value = {
			@Result(property = "id", column = "id"),
			@Result(property = "firstName", column = "first_name"),
			@Result(property = "lastName", column = "last_name"),
			@Result(property = "email", column = "email")
	})
	
	@Select("SELECT id,first_name,last_name,email FROM customers.customer where id=#{id}")
	Customer getCustomerDetailsById(@Param(("id")) int id);
	
	@Select("SELECT * FROM customers.customer")
	List<Customer> getCustomerAllCustomers();
	
	@Select("SELECT COUNT(id) FROM customers.customer")
	Integer countCustomerAllCustomers();
	
	@Select("SELECT COUNT(id) FROM customers.customer")
	List<Integer> countCustomerAllCustomersList();
	
	
	@Select("SELECT id,first_name,last_name,email FROM customers.customer where first_name=#{fname}")
	List<String> getCountryDetailsByName(@Param(("fname")) String fname);
	
	@Select("SELECT date FROM customers.customer where id=#{id}")
	Date getDate(@Param(("id")) String id);
}
