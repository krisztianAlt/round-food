package com.example.roundfood.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.roundfood.controller.collectdata.CustomerDataHandler;
import com.example.roundfood.controller.collectdata.OrderDataHandler;
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.Order;

@Controller
public class RoundFoodErrorController implements ErrorController {
	
	@Autowired
	CustomerDataHandler customerDataHandler;
	
	@Autowired
	OrderDataHandler orderDataHandler;

    @RequestMapping("/error")
    public String handleError(@RequestParam Map<String,String> allRequestParams,
				            Model model,
				            HttpServletRequest httpServletRequest) {

    	Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");
        
        model.addAttribute("loggedIn", customerId != null);
        model.addAttribute("customername", customerName);
        
        if (customerId != null) {
        	Customer customer = customerDataHandler.getCustomerById(customerId);
            Order openedOrder = orderDataHandler.getOpenedOrderByCustomer(customer);
            if (openedOrder != null) {
            	httpServletRequest.getSession().setAttribute("number_of_order_items", openedOrder.getOrderLineItems().size());
            } else {
            	httpServletRequest.getSession().removeAttribute("number_of_order_items");
            }	
        }
        
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}