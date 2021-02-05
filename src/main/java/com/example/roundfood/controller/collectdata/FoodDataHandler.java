package com.example.roundfood.controller.collectdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.roundfood.DAO.FoodDAO;
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.Food;
import com.example.roundfood.model.FoodType;

@Service
public class FoodDataHandler {

	private FoodDAO foodDAO;
	
	public FoodDataHandler(FoodDAO foodDAO) {
		this.foodDAO = foodDAO;
    }

    public Model collectFoodDataByType(FoodType foodType,
									Model model) {
    	
    	List<Food> foods = foodDAO.getFoodsByFoodType(foodType);

    	model.addAttribute("foods", foods);
    	model.addAttribute("foodtype", foodType.name().substring(0, 1) + foodType.name().substring(1).toLowerCase());
    	
        return model;
    }
    
    public Food collectFoodData(Long foodId) {
    	Food food = foodDAO.getFoodById(foodId);
    	return food;
	}
    
}
