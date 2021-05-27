package com.example.roundfood.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.roundfood.controller.collectdata.CustomerDataHandler;
import com.example.roundfood.controller.collectdata.FoodDataHandler;
import com.example.roundfood.controller.collectdata.OrderDataHandler;
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.Food;
import com.example.roundfood.model.Order;

@Controller
public class MainPage {

	@Autowired
	FoodDataHandler foodDataHandler;
	
	@Autowired
	CustomerDataHandler customerDataHandler;
	
	@Autowired
	OrderDataHandler orderDataHandler;
	
	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String renderMainPage(@RequestParam Map<String,String> allRequestParams,
								Model model,
								HttpServletRequest httpServletRequest) {
		
		Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
		String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");
		
		List<Food> foodsInCarouse = foodDataHandler.getFoodsInCarousel();
		
		model.addAttribute("foodsInCarousel", foodsInCarouse);
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
		
		return "welcome";
	}
	
	@RequestMapping(value = {"/about"}, method = RequestMethod.GET)
	public String renderAboutPage(@RequestParam Map<String,String> allRequestParams,
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
		
		return "about";
    }
}
