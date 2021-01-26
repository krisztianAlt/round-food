package com.example.roundfood.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
    List<Order> orders = new ArrayList<>();
    
    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
    private List<FoodPicture> foodPictures = new ArrayList<>();
    
    @ManyToMany
    private List<ExtraTopping> extraToppings;

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

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<FoodPicture> getFoodPictures() {
		return foodPictures;
	}

	public void setFoodPictures(List<FoodPicture> foodPictures) {
		this.foodPictures = foodPictures;
	}

	@Override
	public String toString() {
		return "Food [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", foodType="
				+ foodType + ", orders=" + orders + "]";
	}


}
