package com.example.roundfood.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shippingaddress")
public class ShippingAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String city;
	
    private String postalCode;
    
    private String address;
	
    @ManyToOne()
    private Customer customer;

    public ShippingAddress() {}
    
	public ShippingAddress(String city, String postalCode, String address, Customer customer) {
		this.city = city;
		this.postalCode = postalCode;
		this.address = address;
		this.customer = customer;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "ShippingAddress [city=" + city + ", postalCode=" + postalCode + ", address=" + address + ", customer="
				+ customer + "]";
	}
    
}
