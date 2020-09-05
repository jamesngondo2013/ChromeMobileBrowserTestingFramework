package com.james.api.util.database;

import java.util.Arrays;

import com.james.api.util.database.mapper.CustomerMapper;

import lombok.Data;

@Data
public class SqlMappers {

	SqlDatabase sqlDatabase;
	CustomerMapper customerMapper;
	
	public SqlMappers(SqlDatabase sqlDatabase) {
		this.sqlDatabase = sqlDatabase;
		this.sqlDatabase.setMappers(Arrays.asList(CustomerMapper.class));
	}
	
	public void countryMappers() {
		customerMapper = sqlDatabase.getSqlSession().getMapper(CustomerMapper.class);
	}
// this to be removed use @Data lombok instead
	public CustomerMapper getCustomerMapper() {
		return customerMapper;
	}
	
}
