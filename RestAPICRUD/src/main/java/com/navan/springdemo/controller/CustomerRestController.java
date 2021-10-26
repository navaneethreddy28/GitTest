package com.navan.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navan.springdemo.entity.Customer;
import com.navan.springdemo.entity.CustomerNotFoundException;
import com.navan.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customer")
	public List<Customer> getCustomers(){
		
		return customerService.getCustomers();
		
	}
	@GetMapping("/customer/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		System.out.println("customerId::"+customerId);
		Customer theCustomer = customerService.getCustomer(customerId);
		System.out.println("theCustomer::"+theCustomer);
		if (theCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}
		
		return theCustomer;
	}

	@PostMapping("/customer")
	public Customer addCustomer(@RequestBody Customer customer) {
		
		customer.setId(0);
		customerService.saveCustomer(customer);
		return customer;
		
	}

	// add mapping for PUT /customers - update existing customer
	
	@PutMapping("/customer")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
		
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
		
	}
	
	@DeleteMapping("/customer/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		
		Customer tempCustomer = customerService.getCustomer(customerId);
		
		// throw exception if null
		
		if (tempCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}
				
		customerService.deleteCustomer(customerId);
		
		return "Deleted customer id - " + customerId;
	}
}
