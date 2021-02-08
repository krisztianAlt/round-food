package com.example.roundfood.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "extratopping")
public class ExtraTopping {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
	private String name;
	
	private double price;

	@JsonBackReference
    @ManyToMany(mappedBy = "extraToppings", targetEntity = Food.class)
    private List<Food> foods;

	@JsonBackReference
    @ManyToMany(mappedBy = "selectedExtraToppings", targetEntity = OrderLineItem.class)
    private List<OrderLineItem> orderLineItems;
	
    public ExtraTopping() {
    }

    public ExtraTopping(String name, double price) {
        this.name = name;
        this.price = price;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Food> getFoods() {
		return foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	@Override
	public String toString() {
		return "ExtraTopping [id=" + id + ", name=" + name + ", price=" + price + ", foods=" + foods + "]";
	}
    
}
