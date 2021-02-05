package com.example.roundfood.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.roundfood.controller.collectdata.FoodDataHandler;
import com.example.roundfood.controller.collectdata.OrderLineItemDataHandler;
import com.example.roundfood.model.Food;

@RestController
public class APIController {
	
	Logger logger = LoggerFactory.getLogger(APIController.class);
	
	@Autowired
	FoodDataHandler foodDataHandler;
	
	@Autowired
	OrderLineItemDataHandler orderLineItemDataHandler;
	
	@PostMapping("/food")
    public ResponseEntity<Map<String, Food>> getFoodById(@RequestBody Map<String, String> dataPackageString) { 
		String foodIdString = dataPackageString.get("foodId");
		Map<String, Food> foodMap = new HashMap<String, Food>();
		
		try {
			Long foodId = Long.parseLong(foodIdString);
			Food selectedFood = foodDataHandler.collectFoodData(foodId);
	        foodMap.put("foodData", selectedFood);
		} catch(Exception e) {
			logger.error("Food ID conversion failed: " + e.getMessage());
			return new ResponseEntity<Map<String, Food>>(foodMap, HttpStatus.BAD_REQUEST);
		}
		
        if (foodMap.isEmpty()){
            return new ResponseEntity<Map<String, Food>>(foodMap, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Food>>(foodMap, HttpStatus.OK);
    }
	
	@PostMapping("/add-to-cart")
	public ResponseEntity<Map<String, String>> addToCart(@RequestBody Map<String, Object> dataPackageString,
														HttpServletRequest httpServletRequest){
		Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");
        Long openedorderId = (Long) httpServletRequest.getSession().getAttribute("openedorder_id");
        Integer numberOfOrderItems = (Integer) httpServletRequest.getSession().getAttribute("number_of_order_items");
        
		Long foodId = Long.parseLong((String) dataPackageString.get("foodId"));
		@SuppressWarnings("unchecked")
		List<String> selectedToppings = (List<String>) dataPackageString.get("selectedToppings");
		
		Map responseMap = new HashMap<String, String>();
		responseMap = orderLineItemDataHandler.createNewOrderLineItem(customerId, openedorderId, foodId, selectedToppings);		
		Long orderId = Long.parseLong((String) responseMap.get("orderId"));
		int newNumberOfOrderItems = Integer.parseInt((String) responseMap.get("numberOfOrderItems"));
		
		httpServletRequest.getSession().setAttribute("openedorder_id", orderId);
		httpServletRequest.getSession().setAttribute("number_of_order_items", newNumberOfOrderItems);
		
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.OK);
	} 
	
}