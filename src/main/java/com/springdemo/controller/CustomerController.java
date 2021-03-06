package com.springdemo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springdemo.entity.Customer;
import com.springdemo.exception.CustomerNotFoundException;
import com.springdemo.service.CustomerService;
import com.springdemo.utils.SortingUtils;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customer> getCustomers(@RequestParam(required = false, name = "sort") String sort) throws CustomerNotFoundException
	{
		List<Customer> customers;
		if(sort!=null)
		{
			if(sort.equalsIgnoreCase("firstname"))
				customers=customerService.getCustomers(SortingUtils.FIRST_NAME);
			else if(sort.equalsIgnoreCase("lastname"))
				customers=customerService.getCustomers(SortingUtils.LAST_NAME);
			else if(sort.equalsIgnoreCase("email"))
				customers=customerService.getCustomers(SortingUtils.EMAIL);
			else
				throw new CustomerNotFoundException("Not a valid parameter");
		}
		else
		{
		 customers = customerService.getCustomers(0);
		}
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
	public Customer addCustomer(@Valid @RequestBody Customer customer)
	{
		customer.setId(0);
		customerService.saveCustomer(customer);
		return customer;	
	}
	
	@PutMapping("/customers")
	public Customer updateCustomer(@Valid @RequestBody Customer customer)
	{
		customerService.saveCustomer(customer);
		return customer;	
	}
	
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) throws CustomerNotFoundException
	{
		Customer customer = customerService.getCustomer(customerId);
		if(customer==null)
			throw new CustomerNotFoundException("Customer Id -"+customerId+" does not exist");
		customerService.deleteCustomer(customerId);
		return "Customer Id -"+customerId+" deleted";	
	}
}
