package com.example.roundfood.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.roundfood.DAO.CustomerDAO;
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.ShippingAddress;

public class ShippingAddressDataValidator {

	 private CustomerDAO queryHandler;

	    public ShippingAddressDataValidator(CustomerDAO queryHandler) {
	        this.queryHandler = queryHandler;
	    }

	    public List<String> validateShippingAddressDatas(ShippingAddress shippingAddress) {
	        List<String> errorMessages = new ArrayList();

	        if (shippingAddress.getCity().length() < 2 || shippingAddress.getCity().length() > 30){
	            errorMessages.add("City must be between 2 and 30 characters.");
	        }

	        if (dataContainsNumber(shippingAddress.getCity())){
	            errorMessages.add("City shall be free of numbers.");
	        }
	        
	        if (dataContainsSigns(shippingAddress.getCity())) {
	        	errorMessages.add("City shall be free of signs like '.', ',', ';' etc.");
	        }
	        
	        if (shippingAddress.getCity().length() > 0 && dataNotStartsWithUpperCaseLetter(shippingAddress.getCity())) {
	        	errorMessages.add("City must start with upper case letter.");
	        }

	        if (shippingAddress.getPostalCode().length() != 4){
	            errorMessages.add("Postal code's length must be 4 characters.");
	        }
	        
	        if (dataContainsLetter(shippingAddress.getPostalCode())){
	            errorMessages.add("Postal code shall be free of letters.");
	        }

	        if (dataContainsSigns(shippingAddress.getPostalCode())) {
	        	errorMessages.add("Postal code shall be free of signs like '.', ',', ';' etc.");
	        }
	        
	        if (shippingAddress.getAddress().length() < 5 || shippingAddress.getAddress().length() > 50){
	            errorMessages.add("Address' length must be between 5 and 50 characters.");
	        }

	        if (shippingAddress.getAddress().length() > 0 && dataNotStartsWithUpperCaseLetter(shippingAddress.getAddress())) {
	        	errorMessages.add("Address must start with upper case letter.");
	        }
	        
	        return errorMessages;
	    }

	    private boolean dataNotStartsWithUpperCaseLetter(String word) {
	        return Character.isLowerCase(word.charAt(0));
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

	    private boolean dataContainsLetter(String name) {
	    	if (name.matches("[0-9]+")) {
	    		return false;
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

}
