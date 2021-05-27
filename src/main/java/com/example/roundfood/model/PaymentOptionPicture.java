package com.example.roundfood.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "paymentoptionpicture")
public class PaymentOptionPicture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String fileName;
	
	private String title;
	
	@JsonBackReference
	@ManyToOne
	private PaymentOption paymentOption;
	
	public PaymentOptionPicture() {
	}
	
	public PaymentOptionPicture(String fileName, String title) {
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
	
	public PaymentOption getPaymentOption() {
		return paymentOption;
	}
	
	public void setPaymentOption(PaymentOption paymentOption) {
		this.paymentOption = paymentOption;
	}
    
}
