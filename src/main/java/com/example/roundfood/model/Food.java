package com.example.roundfood.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "food")
public class Food {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	private String description; 
	
	private double price;
	
	@Enumerated(EnumType.STRING)
	private FoodType foodType;
	
	@JsonBackReference
	@OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
	List<OrderLineItem> orderLineItems = new ArrayList<>();
	
	@JsonManagedReference
	@OneToMany(mappedBy = "food", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<FoodPicture> foodPictures = new ArrayList<>();
	
	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, targetEntity = ExtraTopping.class)
	private List<ExtraTopping> extraToppings;
	
	@JsonBackReference
	@OneToOne(cascade = CascadeType.ALL)
	private FoodPictureInMainPageCarousel foodPictureInMainPageCarousel;
	
	public Food() {
	}
	
	public Food(String name, String description, double price, FoodType foodType) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.foodType = foodType;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public FoodType getFoodType() {
		return foodType;
	}
	
	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}
	
	public List<FoodPicture> getFoodPictures() {
		return foodPictures;
	}
	
	public void setFoodPictures(List<FoodPicture> foodPictures) {
		this.foodPictures = foodPictures;
	}
	
	public List<ExtraTopping> getExtraToppings() {
		return extraToppings;
	}
	
	public void setExtraToppings(List<ExtraTopping> extraToppings) {
		this.extraToppings = extraToppings;
	}
	
	public List<OrderLineItem> getOrderLineItems() {
		return orderLineItems;
	}
	
	public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}
	
	public FoodPictureInMainPageCarousel getFoodPictureInMainPageCarousel() {
		return foodPictureInMainPageCarousel;
	}
	
	public void setFoodPictureInMainPageCarousel(FoodPictureInMainPageCarousel foodPictureInMainPageCarousel) {
		this.foodPictureInMainPageCarousel = foodPictureInMainPageCarousel;
	}
	
}