package com.example.roundfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainPage {

	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String renderMainPage() {
        

        return "welcome";
    }
	
}
