package com.example.roundfood.controller;

import com.example.roundfood.controller.collectdata.CustomerDataHandler;
import com.example.roundfood.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class CustomerAccountController {

    @Autowired
    private CustomerDataHandler customerDataHandler;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String renderCustomerRegistration(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("errors", new ArrayList<>());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String renderCustomerRegistration(@ModelAttribute Customer customer,
                                             @RequestParam("confirm") String confirm,
                                             Model model) {

        model = customerDataHandler.collectCustomerRegistrationData(customer, confirm, model);

        List<String> errorMessages = (List) model.asMap().get("errors");

        if (errorMessages.size() == 0 &&
                (boolean) model.asMap().get("savingtried")){
            if ((boolean) model.asMap().get("savingsucceeded")){
                return "redirect:/login-after-registration";
            } else {
                errorMessages.add("Database problem. Please, try later.");
                model.addAttribute("errors", errorMessages);
            }
        }

        return "registration";

    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String renderLogin(@RequestParam Map<String,String> allRequestParams,
                              Model model,
                              HttpServletRequest httpServletRequest) {
    	
        model = customerDataHandler.collectLoginData(allRequestParams, model);

        List<String> errorMessages = (List) model.asMap().get("errors");
        Customer customer = (Customer) model.asMap().get("validcustomer");

        if (errorMessages.size() == 0 && customer != null){
            httpServletRequest.getSession().setAttribute("customer_id", customer.getId());
            httpServletRequest.getSession().setAttribute("customer_name", customer.getFirstName() + " " + customer.getLastName());
            return "redirect:/";
        }

        return "login";

    }

    @RequestMapping(value = "/login-after-registration", method = RequestMethod.GET)
    public String renderPlanetsAfterRegistration(@RequestParam Map<String,String> allRequestParams,
                                Model model,
                                HttpServletRequest httpServletRequest) {

    	model = customerDataHandler.collectLoginData(allRequestParams, model);
    	model.addAttribute("savingsucceeded", true);

        return "login";
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String renderLogout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute("customer_id");
        httpServletRequest.getSession().removeAttribute("customer_name");
        return "redirect:/";
    }
    
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String renderProfile(Model model,
    							HttpServletRequest httpServletRequest) {
    	
    	Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");
        
        model.addAttribute("loggedIn", customerId != null);
        model.addAttribute("customername", customerName);
        
        if (customerId != null) {
        	customerDataHandler.collectCustomerData(customerId, model);
        	model.addAttribute("errors", new ArrayList<>());
        	return "profile";
        }
       
        return "redirect:/";
    }
    
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String renderProfile(@ModelAttribute Customer customer,
				            	Model model,
    							HttpServletRequest httpServletRequest) {
    	
    	Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");
        
    	model = customerDataHandler.collectCustomerProfileModificationData(customer, model);

        List<String> errorMessages = (List) model.asMap().get("errors");

        if (errorMessages.size() == 0 &&
                (boolean) model.asMap().get("updatetried") &&
                !(boolean) model.asMap().get("updatesucceeded")){
        	errorMessages.add("Database problem. Please, try later.");
            model.addAttribute("errors", errorMessages);
        }
        
        model.addAttribute("loggedIn", customerId != null);
        model.addAttribute("customername", customerName);
        
        return "profile";
    }

}
