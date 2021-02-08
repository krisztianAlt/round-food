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
import com.example.roundfood.model.FoodType;

@Controller
public class FoodController {

	@Autowired
	FoodDataHandler foodDataHandler;
	
	@RequestMapping(value = "/foodlist", method = RequestMethod.GET)
    public String renderFoods(@RequestParam Map<String,String> allRequestParams,
				            Model model,
				            HttpServletRequest httpServletRequest) {
		
		Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");
        Long openedorderId = (Long) httpServletRequest.getSession().getAttribute("openedorder_id");
        Integer numberOfOrderItems = (Integer) httpServletRequest.getSession().getAttribute("number_of_order_items");

        model.addAttribute("loggedIn", customerId != null);
        model.addAttribute("customername", customerName);
        httpServletRequest.getSession().setAttribute("openedorder_id", openedorderId);
		httpServletRequest.getSession().setAttribute("number_of_order_items", numberOfOrderItems);
        
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
