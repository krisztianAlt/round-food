package com.example.roundfood.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orderlineitems")
public class OrderLineItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@JsonBackReference
	@ManyToOne
	private Order order;
	
	@JsonManagedReference
	@ManyToOne
	private Food food;
	
	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, targetEntity = ExtraTopping.class)
	private List<ExtraTopping> selectedExtraToppings;

	public OrderLineItem() {
	}
	
	public OrderLineItem(Order order, Food food, List<ExtraTopping> selectedExtraToppings) {
		this.order = order;
		this.food = food;
		this.selectedExtraToppings = selectedExtraToppings;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public List<ExtraTopping> getSelectedExtraToppings() {
		return selectedExtraToppings;
	}

	public void setSelectedExtraToppings(List<ExtraTopping> selectedExtraToppings) {
		this.selectedExtraToppings = selectedExtraToppings;
	}
	
}
