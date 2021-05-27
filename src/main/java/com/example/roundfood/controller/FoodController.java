package com.example.roundfood.controller;

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
import com.example.roundfood.model.FoodType;
import com.example.roundfood.model.Order;

@Controller
public class FoodController {

	@Autowired
	FoodDataHandler foodDataHandler;
	
	@Autowired
	CustomerDataHandler customerDataHandler;
	
	@Autowired
	OrderDataHandler orderDataHandler;
	
	@RequestMapping(value = "/foodlist", method = RequestMethod.GET)
	public String renderFoods(@RequestParam Map<String,String> allRequestParams,
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
		
		String selectedFoodTypeString = allRequestParams.get("type");
		
		if (paramIsAValidFoodType(selectedFoodTypeString)) {
			model = foodDataHandler.collectFoodDataByType(getFoodTypeByName(selectedFoodTypeString), model);
			return "foodlist";	
		}

		return "redirect:/";
	}
	
	private boolean paramIsAValidFoodType(String param) {
		for (FoodType foodType : FoodType.values()) {
			if (foodType.name().equals(param.toUpperCase())) {
				return true;
			}
		}
		return false;
	}
	
	private FoodType getFoodTypeByName(String selectedFoodTypeString) {
		FoodType foodType;
		switch (selectedFoodTypeString.toUpperCase()) {
		case "CAKE":
			foodType = FoodType.CAKE;
			break;
		case "PIZZA":
			foodType = FoodType.PIZZA;
			break;
		case "PIE":
			foodType = FoodType.PIE;
			break;
		default:
			foodType = FoodType.CAKE;
			break;
		}
		return foodType;
	}
}
