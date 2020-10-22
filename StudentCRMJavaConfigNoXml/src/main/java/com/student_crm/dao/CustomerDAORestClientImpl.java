package com.student_crm.dao;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.student_crm.entity.Customer;

@Repository
public class CustomerDAORestClientImpl implements CustomerDAO {

	private RestTemplate restTemplate;
	private String crmRestUrl;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	public CustomerDAORestClientImpl(RestTemplate restTemplate,@Value("${crm.rest.url}") String crmRestUrl) {
		this.restTemplate = restTemplate;
		this.crmRestUrl = crmRestUrl;
		logger.info("Loaded property: crmRestUrl : "+crmRestUrl);
	}
	
	@Override
	public List<Customer> getCustomers() {
		logger.info("in getCustomers(): Calling REST API "+ crmRestUrl);
		 List<Customer> customers = null;
		try {
			// make REST call
			ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(crmRestUrl,// the URL
					HttpMethod.GET,// the HTTP method for GET
					null,// additional request headers or body, null in our case
					new ParameterizedTypeReference<List<Customer>>() {});// the type of the return value. ParameterizedTypeReference is used to pass generic type information:
			// get the list of customers from response
			customers = responseEntity.getBody();
			logger.info("in getCustomers(): customers" + customers);	
		}
		catch (HttpClientErrorException ex) {
		    String message = ex.getResponseBodyAsString();
		    logger.info("message "+message);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public void saveOrUpdate(Customer customer) {
		logger.info("in saveCustomer(): Calling REST API "+ crmRestUrl);
		try {
			int id = customer.getId();
			// make REST call
			if(id == 0) {
				// add customer
				String response =restTemplate.postForObject(crmRestUrl,// the URL
						customer, // the Object to be POSTed
						String.class);// the type of the response
				logger.info("response : "+response);
			}
			else {
				// update employee
							
				restTemplate.put(crmRestUrl,// the URL
						customer);// the Object to PUT
				logger.info("restTemplate.getErrorHandler()"+restTemplate.getErrorHandler());
			}
			logger.info("in saveCustomer(): success");
		}
		catch (HttpClientErrorException ex) {
		    String message = ex.getResponseBodyAsString();
		    logger.info("message "+message);
		}
		catch (Exception e) {
		
			e.printStackTrace();
		}
		
	}

	@Override
	public Customer getCustomer(int id) {
		logger.info("in getCustomer(int id): Calling REST API "+ crmRestUrl);
		Customer customer = null;
		try {
			// make REST call
			customer = restTemplate.getForObject(crmRestUrl+"/"+id,// the URL
					Customer.class);// the type of the return value
			logger.info("in getCustomer(int id): customers" + customer);	
		}
		catch (HttpClientErrorException ex) {
		    String message = ex.getResponseBodyAsString();
		    logger.info("message "+message);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		logger.info("in deleteCustomer(): Calling REST API "+ crmRestUrl);
		try {
			// makre REST call
			restTemplate.delete(crmRestUrl+"/"+id);// the URL
			logger.info("in deleteCustomer(): deleted customer id= "+ id);
		}
		catch (HttpClientErrorException ex) {
		    String message = ex.getResponseBodyAsString();
		    logger.info("message "+message);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
