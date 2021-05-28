package com.example.roundfood.service;

import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.roundfood.DAO.CustomerDAO;
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.CustomerLegitimacy;

class CustomerDataValidatorTest {
	
	private static String goodPassword = "iloveguitars";
	// the password with salted encryption (https://www.appdevtools.com/bcrypt-generator):
	private static String goodPasswordFromDB = "$2a$10$wsv.JTB93NZeQyTrXZ86W.JWZQrLR0PtuvMkPHiuh3zVm/tiKR0YO";
	private static String badPassword = "ihateguitars";
	
	private static String goodConfirmField = "iloveguitars";
	private static String nonMatchingConfirmField = "123akarmi";
	private static String tooShortPassword = "ilov";
	private static String tooLongPassword = "idfezetafg1234iuwsdht";
	private static String passwordWithWhiteSpace = "ilove guitars";
	private static String goodEmail = "guitar@gmail.com";
	private static String badEmail = "drum@gmail.com";
	
	@Test
	void validateRegistrationDatas_GoodDatas_NoErrorMessages() {
		boolean saltedPassWordNeeded = false;
		Customer exampleCustomerWithCorrectDatas = createGoodCustomerDatas(saltedPassWordNeeded);
		CustomerDAO customerDAOMock = Mockito.mock(CustomerDAO.class);
		Password passwordMock = Mockito.mock(Password.class);
		CustomerDataValidator customerDataValidator = new CustomerDataValidator(customerDAOMock, passwordMock);
		List<String> errorMessages = customerDataValidator.validateRegistrationDatas(exampleCustomerWithCorrectDatas, goodConfirmField);
		assertEquals(0, errorMessages.size());
	}
	
	@Test
	void validateRegistrationDatas_NonMatchingConfirmField_OneErrorMessage() {
		boolean saltedPassWordNeeded = false;
		Customer exampleCustomerWithCorrectDatas = createGoodCustomerDatas(saltedPassWordNeeded);
		CustomerDAO customerDAOMock = Mockito.mock(CustomerDAO.class);
		Password passwordMock = Mockito.mock(Password.class);
		CustomerDataValidator customerDataValidator = new CustomerDataValidator(customerDAOMock, passwordMock);
		List<String> errorMessages = customerDataValidator
		        .validateRegistrationDatas(exampleCustomerWithCorrectDatas, nonMatchingConfirmField);
		assertEquals(errorMessages.size(), 1);
		Assertions.assertAll(() -> assertEquals(1, errorMessages.size()),
				() -> assertEquals("Password confirmation failed. Type the same password in Password and Confirm fields.", errorMessages.get(0))
				); 
	}
	
	@Test
	void validateRegistrationDatas_TooShortPassword_OneErrorMessage() {
		boolean saltedPassWordNeeded = false;
		Customer exampleCustomerWithShortPassword = createGoodCustomerDatas(saltedPassWordNeeded);
		exampleCustomerWithShortPassword.setPassword(tooShortPassword);
		String tooShortConfirmField = tooShortPassword;
		CustomerDAO customerDAOMock = Mockito.mock(CustomerDAO.class);
		Password passwordMock = Mockito.mock(Password.class);
		CustomerDataValidator customerDataValidator = new CustomerDataValidator(customerDAOMock, passwordMock);
		List<String> errorMessages = customerDataValidator
		        .validateRegistrationDatas(exampleCustomerWithShortPassword, tooShortConfirmField);
		assertEquals(errorMessages.size(), 1);
		Assertions.assertAll(() -> assertEquals(1, errorMessages.size()),
				() -> assertEquals("Password's length must be between 5 and 20 characters.", errorMessages.get(0))
				); 
	}
	
	@Test
	void validateRegistrationDatas_TooLongPassword_OneErrorMessage() {
		boolean saltedPassWordNeeded = false;
		Customer exampleCustomerWithLongPassword = createGoodCustomerDatas(saltedPassWordNeeded);
		exampleCustomerWithLongPassword.setPassword(tooLongPassword);
		String tooLongConfirmField = tooLongPassword;
		CustomerDAO customerDAOMock = Mockito.mock(CustomerDAO.class);
		Password passwordMock = Mockito.mock(Password.class);
		CustomerDataValidator customerDataValidator = new CustomerDataValidator(customerDAOMock, passwordMock);
		List<String> errorMessages = customerDataValidator
		        .validateRegistrationDatas(exampleCustomerWithLongPassword, tooLongConfirmField);
		assertEquals(errorMessages.size(), 1);
		Assertions.assertAll(() -> assertEquals(1, errorMessages.size()),
				() -> assertEquals("Password's length must be between 5 and 20 characters.", errorMessages.get(0))
				); 
	}
	
	@Test
	void validateRegistrationDatas_PasswordWithWhiteSpace_OneErrorMessage() {
		boolean saltedPassWordNeeded = false;
		Customer exampleCustomerWithWhiteSpaceInPassword = createGoodCustomerDatas(saltedPassWordNeeded);
		exampleCustomerWithWhiteSpaceInPassword.setPassword(passwordWithWhiteSpace);
		String confirmField = passwordWithWhiteSpace;
		CustomerDAO customerDAOMock = Mockito.mock(CustomerDAO.class);
		Password passwordMock = Mockito.mock(Password.class);
		CustomerDataValidator customerDataValidator = new CustomerDataValidator(customerDAOMock, passwordMock);
		List<String> errorMessages = customerDataValidator
		        .validateRegistrationDatas(exampleCustomerWithWhiteSpaceInPassword, confirmField);
		assertEquals(errorMessages.size(), 1);
		Assertions.assertAll(() -> assertEquals(1, errorMessages.size()),
				() -> assertEquals("Password shall be free of white space.", errorMessages.get(0))
				); 
	}
	
	@Test
	void validateRegistrationDatas_FourBadDatas_FourErrorMessages() {
		Customer exampleCustomerWithFourWrongDatas = createBadCustomerDatasFourWrongData();
		CustomerDAO customerDAOMock = Mockito.mock(CustomerDAO.class);
		Password passwordMock = Mockito.mock(Password.class);
		CustomerDataValidator customerDataValidator = new CustomerDataValidator(customerDAOMock, passwordMock);
		List<String> errorMessages = customerDataValidator
		        .validateRegistrationDatas(exampleCustomerWithFourWrongDatas, goodConfirmField);
		Assertions.assertAll(
				() -> assertEquals(errorMessages.size(), 4),
				() -> assertEquals("First name's length must be between 2 and 20 characters.", errorMessages.get(0)),
				() -> assertEquals("Last name shall be free of signs.", errorMessages.get(1)),
				() -> assertEquals("Please, type email in one of these formats: john.doe@fantasymail.com, johndoe@fantasymail.com.", errorMessages.get(2)),
				() -> assertEquals("City can only contain letters, '-' sign and white space, and its length must be between 2 and 25 characters.", errorMessages.get(3))
				);
	}
	
	@Test
	void validateLoginDatas_EmailAddressFoundedInDatabasePasswordIsGood_NoErrorMessage() {
		boolean saltedPassWordNeeded = true;
		Customer exampleCustomerWithCorrectDatas = createGoodCustomerDatas(saltedPassWordNeeded);
		Map<String,String> goodLoginDatas = createGoodLoginDatas();
		CustomerDAO customerDAOMock = Mockito.mock(CustomerDAO.class);
		Password passwordService = new Password();
		CustomerDataValidator customerDataValidator = new CustomerDataValidator(customerDAOMock, passwordService);
		when(customerDAOMock.getCustomerByEmail(goodEmail)).thenReturn(exampleCustomerWithCorrectDatas);
		List<String> errorMessages = (ArrayList) customerDataValidator
				.validateLoginDatas(goodLoginDatas).get("errors");
		assertEquals(0, errorMessages.size());
	}

	@Test
	void validateLoginDatas_EmailAddressFoundedInDatabasePasswordIsBad_OneErrorMessage() {
		boolean saltedPassWordNeeded = true;
		Customer exampleCustomerWithCorrectDatas = createGoodCustomerDatas(saltedPassWordNeeded);
		boolean wrongPasswordNeeded = true;
		boolean wrongEmailNeeded = false;
		Map<String,String> exampleBadLoginDatasWrongPassword = createBadLoginDatas(wrongPasswordNeeded, wrongEmailNeeded);
		CustomerDAO customerDAOMock = Mockito.mock(CustomerDAO.class);
		Password passwordService = new Password();
		CustomerDataValidator customerDataValidator = new CustomerDataValidator(customerDAOMock, passwordService);
		when(customerDAOMock.getCustomerByEmail(goodEmail)).thenReturn(exampleCustomerWithCorrectDatas);
		List<String> errorMessages = (ArrayList) customerDataValidator
				.validateLoginDatas(exampleBadLoginDatasWrongPassword).get("errors");
		Assertions.assertAll(
				() -> assertEquals(1, errorMessages.size()),
				() -> assertEquals("Invalid email or password.", errorMessages.get(0))
				);
	}
	
	@Test
	void validateLoginDatas_EmailAddressNotFoundedInDatabase_OneErrorMessage() {
	boolean saltedPassWordNeeded = true;
	Customer exampleCustomerWithCorrectDatas = createGoodCustomerDatas(saltedPassWordNeeded);
	boolean wrongPasswordNeeded = false;
	boolean wrongEmailNeeded = true;
	Map<String,String> exampleBadLoginDatasWrongEmail = createBadLoginDatas(wrongPasswordNeeded, wrongEmailNeeded);
	CustomerDAO customerDAOMock = Mockito.mock(CustomerDAO.class);
	Password passwordService = new Password();
	CustomerDataValidator customerDataValidator = new CustomerDataValidator(customerDAOMock, passwordService);
	when(customerDAOMock.getCustomerByEmail(badEmail)).thenReturn(null);
	List<String> errorMessages = (ArrayList) customerDataValidator
			.validateLoginDatas(exampleBadLoginDatasWrongEmail).get("errors");
	Assertions.assertAll(
			() -> assertEquals(1, errorMessages.size()),
			() -> assertEquals("Invalid email or password.", errorMessages.get(0))
			);
	}
	
	private static Customer createGoodCustomerDatas(boolean saltedPassword) {
		Customer exampleCorrectCustomer = new Customer();
		exampleCorrectCustomer.setFirstName("Jimmy");
		exampleCorrectCustomer.setLastName("Hendrix");
		exampleCorrectCustomer.setEmail(goodEmail);
		exampleCorrectCustomer.setPhoneNumber("30-1234567");
		exampleCorrectCustomer.setLegitimacy(CustomerLegitimacy.USER);
		exampleCorrectCustomer.setCity("Budapest");
		exampleCorrectCustomer.setPostalCode("1234");
		exampleCorrectCustomer.setAddress("Kodály Zoltán utca 20.");

		if (saltedPassword) {
			exampleCorrectCustomer.setPassword(goodPasswordFromDB);
		} else {
			exampleCorrectCustomer.setPassword(goodPassword);
		}
		
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
		exampleCustomerWithWrongDatas.setPassword(goodPassword);
		return exampleCustomerWithWrongDatas;
	}
	
	private static Map<String, String> createGoodLoginDatas() {
		Map<String, String> exampleCorrectLoginDatas = new HashMap<>();
		exampleCorrectLoginDatas.put("email", "guitar@gmail.com");
		exampleCorrectLoginDatas.put("password", goodPassword);
		return exampleCorrectLoginDatas;
	}
	
	private static Map<String, String> createBadLoginDatas(boolean badPasswordNeeded, boolean badEmailNeeded) {
		Map<String, String> exampleBadLoginDatas = new HashMap<>();
		
		if (badEmailNeeded) {
			exampleBadLoginDatas.put("email", badEmail);
		} else {
			exampleBadLoginDatas.put("email", goodEmail);	
		}
	
		if (badPasswordNeeded) {
			exampleBadLoginDatas.put("password", badPassword);	
		} else {
			exampleBadLoginDatas.put("password", goodPassword);
		}
		
		return exampleBadLoginDatas;
	}
    
}