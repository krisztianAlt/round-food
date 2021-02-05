package com.example.roundfood.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="paymentoption")
public class PaymentOption {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	private String name;
	
	@JsonManagedReference
    @OneToMany(mappedBy = "paymentOption", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PaymentOptionPicture> paymentOptionPicture = new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "paymentOption", fetch = FetchType.LAZY)
    List<Order> orders = new ArrayList<>();

	public PaymentOption() {
	}
	
	public PaymentOption(long id, String name) {
		this.id = id;
		this.name = name;
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

	public List<PaymentOptionPicture> getPaymentOptionPicture() {
		return paymentOptionPicture;
	}

	public void setPaymentOptionPicture(List<PaymentOptionPicture> paymentOptionPicture) {
		this.paymentOptionPicture = paymentOptionPicture;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
}
