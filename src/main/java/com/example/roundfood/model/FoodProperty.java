package com.example.roundfood.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "foodproperty")
public class FoodProperty {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
	private String name;
	
	private String faId;

    @ManyToMany(mappedBy = "foodProperties")
    private List<Food> foods;

    public FoodProperty() {
    }

    public FoodProperty(String name, String faId) {
        this.name = name;
        this.faId = faId;
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

	public String getFaId() {
		return faId;
	}

	public void setFaId(String faId) {
		this.faId = faId;
	}

	public List<Food> getFoods() {
		return foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	@Override
	public String toString() {
		return "FoodProperty [id=" + id + ", name=" + name + ", faId=" + faId + ", foods=" + foods + "]";
	}
    
}
