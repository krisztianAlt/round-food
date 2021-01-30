package com.example.roundfood.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.roundfood.controller.collectdata.FoodDataHandler;
import com.example.roundfood.model.Food;

@RestController
public class APIController {
	
	@Autowired
	FoodDataHandler foodDataHandler;
	
	@PostMapping("/food")
    public ResponseEntity<Map<String, Food>> getFoodById(@RequestBody Map<String, String> dataPackageString) { 
		String foodIdString = dataPackageString.get("foodId");
		Map foodMap = new HashMap<String, Object>();
		
		try {
			Long foodId = Long.parseLong(foodIdString);
			Optional<Food> selectedFood = foodDataHandler.collectFoodData(foodId);
	        foodMap.put("foodData", selectedFood);
		} catch(Exception e) {
			System.out.print("Food ID conversion failed: " + e.getMessage());
			return new ResponseEntity<Map<String, Food>>(foodMap, HttpStatus.BAD_REQUEST);
		}
		
        if (foodMap.isEmpty()){
            return new ResponseEntity<Map<String, Food>>(foodMap, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Food>>(foodMap, HttpStatus.OK);
    }
	
	@PostMapping("/add-to-cart")
	public ResponseEntity<Map<String, String>> addToCart(@RequestBody Map<String, Object> dataPackageString){
		String foodIdString = (String) dataPackageString.get("foodId");
		@SuppressWarnings("unchecked")
		List<String> selectedToppingsString = (List<String>) dataPackageString.get("selectedToppings");
		
		System.out.println(foodIdString);
		System.out.println(selectedToppingsString);
		
		Map responseMap = new HashMap<String, String>();
		
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.OK);
	} 
	
}