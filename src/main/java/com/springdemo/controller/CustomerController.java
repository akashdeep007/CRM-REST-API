package com.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springdemo.entity.Customer;
import com.springdemo.exception.CustomerNotFoundException;
import com.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customer> getCustomers() throws CustomerNotFoundException
	{
		List<Customer> customers = customerService.getCustomers(0);
		if(customers.isEmpty())
			throw new CustomerNotFoundException("No records exist");
		return customers;
	}
	
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) throws CustomerNotFoundException
	{
		Customer customer = customerService.getCustomer(customerId);
		if(customer==null)
			throw new CustomerNotFoundException("Customer Id -"+customerId+" does not exist");
		return customer;
	}
	
	@PostMapping("/customers")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Customer addCustomer(@RequestBody Customer customer)
	{
		customer.setId(0);
		customerService.saveCustomer(customer);
		return customer;	
	}
}
