package com.example.roundfood.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="foodpictureinmainpagecarousel")
public class FoodPictureInMainPageCarousel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    @JsonManagedReference
    @OneToOne(mappedBy = "foodPictureInMainPageCarousel")
    private Food food;

    public FoodPictureInMainPageCarousel() {
	}
    
	public FoodPictureInMainPageCarousel(String fileName) {
		super();
		this.fileName = fileName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}
	
}
