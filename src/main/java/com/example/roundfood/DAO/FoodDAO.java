package com.example.roundfood.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.roundfood.model.Food;
import com.example.roundfood.model.FoodType;
import com.example.roundfood.repository.FoodRepository;

@Service
public class FoodDAO {

	@Autowired
    private FoodRepository foodRepository;
	
	public Optional<Food> getFoodById(Long foodId) {
        Optional<Food> food = null;
        try{
            food = foodRepository.findById(foodId);
        } catch (Exception e){
            System.out.println("No record found: " + e.getMessage());
        }
        return food;
    }
	
	public List<Food> getFoodsByFoodType(FoodType foodType) {
		
		List<Food> foods = new ArrayList<>();
		try{
            foods = foodRepository.getFoodsByFoodType(foodType);
        } catch (Exception e) {
            System.out.println("No record found: " + e.getMessage());
        }
		return foods;
	}
	
}
