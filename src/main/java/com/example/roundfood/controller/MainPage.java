package com.example.roundfood.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.roundfood.controller.collectdata.FoodDataHandler;

@Controller
public class MainPage {

	@Autowired
    FoodDataHandler foodDataHandler;
	
	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String renderMainPage(@RequestParam Map<String,String> allRequestParams,
					            Model model,
					            HttpServletRequest httpServletRequest) {
		
		foodDataHandler.collectFoodTypeData(allRequestParams, model, httpServletRequest);
        
		return "welcome";
    }
	
	@RequestMapping(value = {"/about"}, method = RequestMethod.GET)
    public String renderAboutPage(@RequestParam Map<String,String> allRequestParams,
					            Model model,
					            HttpServletRequest httpServletRequest) {
        return "about";
    }
}
