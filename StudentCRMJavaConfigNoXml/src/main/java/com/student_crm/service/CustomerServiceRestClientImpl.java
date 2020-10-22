package com.student_crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student_crm.dao.CustomerDAO;
import com.student_crm.entity.Customer;

@Service
public class CustomerServiceRestClientImpl implements CustomerService {

	// Inject CustomerDAO
		@Autowired
		private CustomerDAO customerDAO;
		
	@Override
	public List<Customer> getCustomers() {
		List<Customer> customers = customerDAO.getCustomers();
		return customers;
	}

	@Override
	public void saveOrUpdate(Customer customer) {
		customerDAO.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int id) {
		Customer customer = customerDAO.getCustomer(id);
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		customerDAO.deleteCustomer(id);
	}

	
}
