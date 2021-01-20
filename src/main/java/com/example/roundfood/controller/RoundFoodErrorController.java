package com.example.roundfood.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoundFoodErrorController implements ErrorController  {

    @RequestMapping("/error")
    public String handleError(@RequestParam Map<String,String> allRequestParams,
				            Model model,
				            HttpServletRequest httpServletRequest) {

    	Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");
        model.addAttribute("loggedIn", customerId != null);
        model.addAttribute("customername", customerName);
        
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}