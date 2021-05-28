package com.example.roundfood.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.roundfood.DAO.CustomerDAO;
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.CustomerLegitimacy;

class CustomerDataValidatorTest {

	private static Customer exampleCustomerWithCorrectDatas = createGoodCustomerDatas();
	private static Customer exampleCustomerWithFourWrongDatas = createBadCustomerDatasFourWrongData();
	private String goodConfirmField = "idfeiurh87";
	private String badConfirmField = "123akarmi";
	
	private static Customer createGoodCustomerDatas() {
		Customer exampleCorrectCustomer = new Customer();
		exampleCorrectCustomer.setFirstName("Jimmy");
		exampleCorrectCustomer.setLastName("Hendrix");
		exampleCorrectCustomer.setEmail("guitar@gmail.com");
		exampleCorrectCustomer.setPhoneNumber("30-1234567");
		exampleCorrectCustomer.setLegitimacy(CustomerLegitimacy.USER);
		exampleCorrectCustomer.setCity("Budapest");
		exampleCorrectCustomer.setPostalCode("1234");
		exampleCorrectCustomer.setAddress("Kodály Zoltán utca 20.");
		exampleCorrectCustomer.setPassword("idfeiurh87");
		return exampleCorrectCustomer;
	}
	
	private static Customer createBadCustomerDatasFourWrongData() {
		Customer exampleCustomerWithWrongDatas = new Customer();
		exampleCustomerWithWrongDatas.setFirstName(""); // missing first name
		exampleCustomerWithWrongDatas.setLastName("Hend;ix"); // sign in human last name
		exampleCustomerWithWrongDatas.setEmail("missingdothu@freemail"); // missing .hu, .com etc. element
		exampleCustomerWithWrongDatas.setPhoneNumber("30-1234567");
		exampleCustomerWithWrongDatas.setLegitimacy(CustomerLegitimacy.USER);
		exampleCustomerWithWrongDatas.setCity("Buda2pest"); // number in city name
		exampleCustomerWithWrongDatas.setPostalCode("1234");
		exampleCustomerWithWrongDatas.setAddress("Kodály Zoltán utca 20.");
		exampleCustomerWithWrongDatas.setPassword("idfeiurh87");
		return exampleCustomerWithWrongDatas;
	}
	
	@Test
	void validateRegistrationDatas_GoodDatas_NoErrorMessages() {
		CustomerDAO customerDAOMock = Mockito.mock(CustomerDAO.class);
		CustomerDataValidator customerDataValidator = new CustomerDataValidator(customerDAOMock);
		List<String> errorMessages = customerDataValidator.validateRegistrationDatas(exampleCustomerWithCorrectDatas, goodConfirmField);
		assertEquals(errorMessages.size(), 0);
	}
	
	@Test
	void validateRegistrationDatas_BadConfirm_OneErrorMessage() {
		CustomerDAO customerDAOMock = Mockito.mock(CustomerDAO.class);
		CustomerDataValidator customerDataValidator = new CustomerDataValidator(customerDAOMock);
		List<String> errorMessages = customerDataValidator
		        .validateRegistrationDatas(exampleCustomerWithCorrectDatas, badConfirmField);
		assertEquals(errorMessages.size(), 1);
	}
	
	@Test
	void validateRegistrationDatas_BadDatas_FourErrorMessages() {
		CustomerDAO customerDAOMock = Mockito.mock(CustomerDAO.class);
		CustomerDataValidator customerDataValidator = new CustomerDataValidator(customerDAOMock);
		List<String> errorMessages = customerDataValidator
		        .validateRegistrationDatas(exampleCustomerWithFourWrongDatas, goodConfirmField);
		
		for(String errorMessage : errorMessages) {
			System.out.println(errorMessage);
		}
		assertEquals(errorMessages.size(), 4);
	}
	
}
