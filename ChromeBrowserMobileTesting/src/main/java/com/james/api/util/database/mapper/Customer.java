package com.james.api.util.database.mapper;

public class Customer {

	private String firstName;
	private String lastName;
	private String email;
	private int id;
	
	public Customer(int id, String firstname, String lastname, String email) {
		setId(id);
		setFirst_name(firstName);
		setLast_name(lastName);
		setEmail(email);
	}
	
	public Customer() {
		setId(id);
		setFirst_name(firstName);
		setLast_name(lastName);
		setEmail(email);
	}

	public String getFirst_name() {
		return firstName;
	}

	public void setFirst_name(String first_name) {
		this.firstName = first_name;
	}

	public String getLast_name() {
		return lastName;
	}

	public void setLast_name(String last_name) {
		this.lastName = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
