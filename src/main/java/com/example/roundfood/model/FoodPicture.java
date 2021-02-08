package com.example.roundfood.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "foodpicture")
public class FoodPicture {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    private String title;

    @JsonBackReference
    @ManyToOne
    private Food food;

    public FoodPicture() {
    }

    public FoodPicture(String fileName, String title) {

        this.fileName = fileName;
        this.title = title;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	@Override
	public String toString() {
		return "FoodPicture [id=" + id + ", fileName=" + fileName + ", title=" + title
				+ ", food=" + food + "]";
	}
    
}
