package com.example.roundfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.roundfood.model.Food;
import com.example.roundfood.model.FoodType;

public interface FoodRepository extends JpaRepository<Food, Long> {

	Food getOne(Long id);
	
	List<Food> getFoodsByFoodType(FoodType foodType);
	
}
