package com.example.roundfood.service;

import java.util.regex.Pattern;

public enum InputFieldPattern {
	EMAIL ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"),
	PHONE ("\\d{1,2}-\\d{6,7}"),
	CITY ("^[a-zA-Z -íÍöÖüÜóÓőŐúÚéÉáÁ]{2,25}$"),
	POSTAL_CODE ("^[0-9]{4}$"),
	ADDRESS ("^[a-zA-Z0-9,. -íÍöÖüÜóÓőŐúÚéÉáÁ]{5,35}$"); 
	
	private String regex;
	
	InputFieldPattern(String regex) {
		this.regex = regex;
	}
	
	public String getRegex() {
		return regex;
	}
	
	public boolean validate(String inputValue) {
		Pattern compiledPattern = Pattern.compile(this.getRegex());
		return compiledPattern.matcher(inputValue).matches();
	}
}
