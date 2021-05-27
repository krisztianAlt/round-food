package com.example.roundfood.controller.collectdata;

import com.example.roundfood.DAO.CustomerDAO;
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.CustomerLegitimacy;
import com.example.roundfood.service.CustomerDataValidator;
import com.example.roundfood.service.Password;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerDataHandler{
	
	Logger logger = LoggerFactory.getLogger(CustomerDataHandler.class);
	
	private CustomerDAO customerDAO;
	private CustomerDataValidator customerDataValidator;
	private Password password;
	
	public CustomerDataHandler(CustomerDAO customerDAO, CustomerDataValidator customerDataValidator, Password password) {
		this.customerDAO = customerDAO;
		this.customerDataValidator = customerDataValidator;
		this.password = password;
	}

	public Model collectCustomerRegistrationData(Customer customer, 
			String confirm, 
			Model model) {
		List<String> errorMessages;
		boolean savingSucceeded = false;
		boolean savingTried = false;
	
		errorMessages = customerDataValidator.validateRegistrationDatas(customer, confirm);
	
		if (errorMessages.size() == 0){
			savingTried = true;
			savingSucceeded = saveCustomerDatas(customer);
		}
		
		if (savingSucceeded) {
			logger.info("NEW CUSTOMER REGISTERED, ID: " + String.valueOf(customer.getId()));
		}
		
		model.addAttribute("customer", customer);
		model.addAttribute("errors", errorMessages);
		model.addAttribute("savingsucceeded", savingSucceeded);
		model.addAttribute("savingtried", savingTried);
		
		return model;
	}
	
	public Model collectLoginData(@RequestParam Map<String,String> allRequestParams,
								Model model) {
		
		List<String> errorMessages = new ArrayList();
		Map<String, String> customerDatas = new HashMap<>();
		Customer customer = null;
		if (allRequestParams.size() > 0){
			customerDatas.put("email", allRequestParams.get("email"));
			customerDatas.put("password", allRequestParams.get("password"));
			
			Map<String, Object> errorMessagesAndCustomer = customerDataValidator.validateLoginDatas(customerDatas);
			errorMessages = (List<String>) errorMessagesAndCustomer.get("errors");
			customer = (Customer) errorMessagesAndCustomer.get("customer");
		}
		
		model.addAttribute("customer", customerDatas);
		model.addAttribute("errors", errorMessages);
		model.addAttribute("validcustomer", customer);
		
		return model;
	}
	
	public boolean checkUserIsAdmin(long customerId) {
		Customer customer = customerDAO.getCustomerById(customerId);
		if (customer.getLegitimacy() == CustomerLegitimacy.ADMIN) {
			return true;
		}
		return false;
	}
	
	public boolean checkUserIsLoggedUserOrAdmin(long customerId) {
		Customer customer = customerDAO.getCustomerById(customerId);
		if (customer.getLegitimacy() == CustomerLegitimacy.ADMIN || customer.getLegitimacy() == CustomerLegitimacy.USER ) {
			return true;
		}
		return false;
	}
	
	public void collectCustomerData(Long customerId,
									Model model) {
		Customer customer = customerDAO.getCustomerById(customerId);
		model.addAttribute("customer", customer);	
	}
	
	public Customer getCustomerById(Long id) {
		Customer customer = customerDAO.getCustomerById(id);
		return customer;
	}
	
	public Model collectCustomerProfileModificationData(Customer customer, Model model) {
		List<String> errorMessages;
		boolean updateSucceeded = false;
		boolean updateTried = false;
		
		errorMessages = customerDataValidator.validateModificationDatas(customer);
	
		if (errorMessages.size() == 0){
			updateTried = true;
			updateSucceeded = updateCustomerDatas(customer);
		}
		
		if (updateSucceeded) {
			logger.info("CUSTOMER UPDATED, ID: " + String.valueOf(customer.getId()));
		}
		
		model.addAttribute("customer", customerDAO.getCustomerById(customer.getId()));
		model.addAttribute("errors", errorMessages);
		model.addAttribute("updatesucceeded", updateSucceeded);
		model.addAttribute("updatetried", updateTried);
		
		return model;
	}
	
	public Model collectCustomerPasswordModificationData(Model model, Long customerId, String currentPassword, String newPassword) {
		List<String> passwordErrorMessages;
		boolean updateSucceeded = false;
		boolean updateTried = false;
		
		passwordErrorMessages = customerDataValidator.validatePasswordModificationData(customerId, currentPassword, newPassword);
		
		if (passwordErrorMessages.size() == 0){
			updateTried = true;
			updateSucceeded = updateCustomerPassword(customerId, newPassword);
		}
		
		model.addAttribute("passworderrors", passwordErrorMessages);
		model.addAttribute("passwordupdatesucceeded", updateSucceeded);
		model.addAttribute("updatetried", updateTried);
		
		return model;
	}
	
	public boolean deleteUser(Long customerId) {
		boolean deletionSucceeded = false;
		deletionSucceeded = customerDAO.deleteUser(customerId);
		
		if (deletionSucceeded) {
			logger.info("CUSTOMER DELETED, ID: " + String.valueOf(customerId));
		}
		
		return deletionSucceeded;
	}
	
	private boolean saveCustomerDatas(Customer customer) {
		boolean savingSucceeded = false;
		
		String passwordStr = customer.getPassword();
		String hashPassword = password.hashPassword(passwordStr);
		customer.setPassword(hashPassword);
		customer.setLegitimacy(CustomerLegitimacy.USER);
		
		try {
			customerDAO.saveNewCustomer(customer);
			savingSucceeded = true;
		} catch (Exception e){
			logger.error("SAVING FAILED: " + e.getMessage());
		}
		return savingSucceeded;
	}
	
	private boolean updateCustomerDatas(Customer customer) {
		boolean updateSucceeded = false;
		
		try {
			customerDAO.updateCustomer(customer);
			updateSucceeded = true;
		} catch (Exception e){
			logger.error("UPDATING FAILED: " + e.getMessage());
		}
		return updateSucceeded;
		
	}
	
	private boolean updateCustomerPassword(Long customerId, String newPassword) {
		boolean updateSucceeded = false;
	
		String hashPassword = password.hashPassword(newPassword);
	
		try {
			customerDAO.updateCustomerPassword(customerId, hashPassword);
			updateSucceeded = true;
		} catch (Exception e){
			logger.error("UPDATING PASSWORD FAILED: " + e.getMessage());
		}
		return updateSucceeded;
	}
}
