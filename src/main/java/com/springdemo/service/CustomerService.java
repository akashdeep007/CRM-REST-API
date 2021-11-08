package com.springdemo.service;

import java.util.List;


import com.springdemo.entity.Customer;

public interface CustomerService {
	
	public List<Customer> getCustomers(int sort);

	public void saveCustomer(Customer customer);

	public Customer getCustomer(int id);

	public void deleteCustomer(int id);

	public List<Customer> searchCustomer(String name);

}
