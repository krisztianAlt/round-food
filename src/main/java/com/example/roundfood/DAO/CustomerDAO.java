package com.example.roundfood.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roundfood.model.Customer;
import com.example.roundfood.repository.CustomerRepository;


@Service
public class CustomerDAO {

	@Autowired
    private CustomerRepository customerRepository;
	
	public void saveNewCustomer(Customer customer) {

        customerRepository.save(customer);

    }
	
	public void updateCustomer(Customer updatedCustomer) {
		Customer customer = customerRepository.getOne(updatedCustomer.getId());
		
		customer.setFirstName(updatedCustomer.getFirstName());
		customer.setLastName(updatedCustomer.getLastName());
		customer.setEmail(updatedCustomer.getEmail());
		customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
		
		customerRepository.save(customer);
	}
	
	
	public Customer getCustomerById(Long customerId) {
        Customer customer = null;
        try{
            customer = customerRepository.getOne(customerId);
        } catch (Exception e){
            System.out.println("No record found: " + e.getMessage());
        }
        return customer;
    }
	
	 public Customer getCustomerByEmail(String email) {
        Customer customer = null;
        try{
            customer = customerRepository.findByEmail(email);
        } catch (Exception e){
            System.out.println("No record found: " + e.getMessage());
        }
        return customer;
    }

}
