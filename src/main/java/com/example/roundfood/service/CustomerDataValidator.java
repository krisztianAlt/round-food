package com.example.roundfood.service;

import com.example.roundfood.DAO.CustomerDAO;
import com.example.roundfood.model.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerDataValidator {

    private CustomerDAO queryHandler;

    public CustomerDataValidator(CustomerDAO queryHandler) {
        this.queryHandler = queryHandler;
    }
    @Autowired
    private Password password;

    public List<String> validateRegistrationDatas(Customer customer, String confirm) {
        List<String> errorMessages = validatePersonalData(customer, "registration");
        
        if (customer.getPassword().length() < 5 || customer.getPassword().length() > 20){
            errorMessages.add("Password's length must be between 5 and 20 characters.");
        }

        if (!customer.getPassword().equals(confirm)){
            errorMessages.add("Password confirmation failed. Type the same password in Password and Confirm fields.");
        }
        
        if (passwordContainsSpace(customer.getPassword())) {
        	errorMessages.add("Password shall be free of white space.");
        }
        
        return errorMessages;
    }

    public List<String> validateModificationDatas(Customer customer) {
    	List<String> errorMessages = validatePersonalData(customer, "modification");
    	return errorMessages;
    }
    
    public List<String> validatePasswordModificationData(Long customerId, String currentPassword, String newPassword){
    	 List<String> errorMessages = new ArrayList();
    	 Customer customer = queryHandler.getCustomerById(customerId);
    	 
    	 if (currentPassword.equals(newPassword)) {
    		 errorMessages.add("New password must be different from current.");
    	 }
    	 
    	 if (!password.checkPassword(currentPassword, customer.getPassword())) {
    		 errorMessages.add("Wrong current password.");
    	 }
    	 
         if (newPassword.length() < 5 || newPassword.length() > 20){
             errorMessages.add("New password's length must be between 5 and 20 characters.");
         }

         if (passwordContainsSpace(newPassword)) {
         	errorMessages.add("New password shall be free of white space.");
         }
         
         return errorMessages;
    }
    
    private List<String> validatePersonalData(Customer customer, String mode) {
    	
    	List<String> errorMessages = new ArrayList();
    	
    	if (customer.getFirstName().length() < 2 || customer.getFirstName().length() > 20){
            errorMessages.add("First name's length must be between 2 and 20 characters.");
        }
        
        if (dataContainsNumber(customer.getFirstName())){
            errorMessages.add("First name shall be free of numbers.");
        }
        if (dataContainsSigns(customer.getFirstName())){
            errorMessages.add("First name shall be free of signs.");
        }
        
        if (customer.getFirstName().length() > 0 && dataNotStartsWithUpperCaseLetter(customer.getFirstName())){
            errorMessages.add("First name must start with upper case letter.");
        }
        if (customer.getLastName().length() < 2 || customer.getLastName().length() > 20){
            errorMessages.add("Last name' length must be between 2 and 20 characters.");
        }
        
        if (dataContainsNumber(customer.getLastName())){
            errorMessages.add("Last name shall be free of numbers.");
        }
        
        if (dataContainsSigns(customer.getLastName())){
            errorMessages.add("Last name shall be free of signs.");
        }
        
        if (customer.getLastName().length() > 0 && dataNotStartsWithUpperCaseLetter(customer.getLastName())){
            errorMessages.add("Last name must start with upper case letter.");
        }

        Pattern compiledPatternEmail = Pattern.compile(
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcherEmail = compiledPatternEmail.matcher(customer.getEmail());
        boolean emailIsCorrect = matcherEmail.matches();
        if (!emailIsCorrect){
            errorMessages.add("Please, type email in one of these formats: john.doe@fantasymail.com, johndoe@fantasymail.com.");
        }

        if (mode.equals("registration") && emailExists(customer.getEmail())) {
        	errorMessages.add("This email is already exists in our database. Please, give another one.");
        }
        
        if (mode.equals("modification") && anotherUserIsUsingEmail(customer)) {
        	errorMessages.add("Another user is already using this email address. Please, type another one.");
        }

        Pattern compiledPatternPhone = Pattern.compile("\\d{2,3}-\\d{6,10}");
        Matcher matcherPhone = compiledPatternPhone.matcher(customer.getPhoneNumber());
        boolean phoneIsCorrect = matcherPhone.matches();
        if (!phoneIsCorrect) {
        	errorMessages.add("Please, type phone number in these format: 30-1234567.");
        }
        
        if (!phoneNumberContainsCorrectCharacters(customer.getPhoneNumber())) {
        	errorMessages.add("Phone number can only contain digits and '-' sign.");
        }
        
		return errorMessages;
    }
    
    private boolean dataNotStartsWithUpperCaseLetter(String word) {
        return Character.isLowerCase(word.charAt(0));
    }

    private boolean emailExists(String email) {
        boolean emailExists = false;
        Customer customer = queryHandler.getCustomerByEmail(email);
        if (customer != null){
            emailExists = true;
        }
        return emailExists;
    }

    private boolean anotherUserIsUsingEmail(Customer customer) {
    	boolean anotherUserIsUsingEmail = false;
    	Customer customerInDB = queryHandler.getCustomerByEmail(customer.getEmail());
    	
    	if (customerInDB != null && 
    		customer.getId() != customerInDB.getId()){
    		
    		anotherUserIsUsingEmail = true;
        }
    	
    	return anotherUserIsUsingEmail;
    }
    
    private boolean dataContainsNumber(String name) {
        String numbers = "1234567890";
        for (int index = 0; index < name.length(); index++){
            if (numbers.contains(Character.toString(name.charAt(index)))){
                return true;
            }
        }
        return false;
    }
    
    private boolean phoneNumberContainsCorrectCharacters(String name) {
        String correctCharacters = "1234567890-";
        for (int index = 0; index < name.length(); index++){
            if (!correctCharacters.contains(Character.toString(name.charAt(index)))){
                return false;
            }
        }
        return true;
    }

    private boolean dataContainsSigns(String name) {
        String signs = ".,;/=*-";
        for (int index = 0; index < name.length(); index++){
            if (signs.contains(Character.toString(name.charAt(index)))){
                return true;
            }
        }
        return false;
    }

    private boolean passwordContainsSpace(String password) {
    	String whiteSpaceString = " ";
    	if (password.contains(whiteSpaceString)) {
    		return true;
    	}
    	return false;
    }
    
    public Map<String, Object> validateLoginDatas(Map<String, String> customerDatas) {
        String email = customerDatas.get("email");
        String passwordStr = customerDatas.get("password");

        List<String> errorMessages = new ArrayList();

        Customer customerFromDB = queryHandler.getCustomerByEmail(email);

        if (customerFromDB == null) {
            errorMessages.add("Invalid email or password.");
        } else {
            if (!password.checkPassword(passwordStr,customerFromDB.getPassword())) {
                errorMessages.add("Invalid email or password.");
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("errors", errorMessages);
        result.put("customer", customerFromDB);
        return result;
    }
    
}