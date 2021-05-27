package com.example.roundfood.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phoneNumber;
    
    private String city;
	
    private String postalCode;
    
    private String address;
	
    @JsonIgnore
    private String password;
    
    @Enumerated(EnumType.STRING)
    private CustomerLegitimacy legitimacy;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Order> orders;

	/* Older version:
	@JsonManagedReference
	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
	private List<ShippingAddress> shippingAddresses;
	*/

	public Customer() {
	}

	public Customer(String firstName, String lastName, String email, 
					String phoneNumber, String password, CustomerLegitimacy legitimacy,
					String city, String postalCode, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.legitimacy = legitimacy;
		this.city = city;
		this.postalCode = postalCode;
		this.address = address;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public CustomerLegitimacy getLegitimacy() { return legitimacy; }
	
	public void setLegitimacy(CustomerLegitimacy legitimacy) { this.legitimacy = legitimacy; }
	
	
	public List<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(List<Order> order) {
		this.orders = order;
	}
	
	/* Older version:
	
	public List<ShippingAddress> getShippingAddress() {
		return shippingAddresses;
	}
	
	public void setShippingAddress(List<ShippingAddress> shippingAddresses) {
		this.shippingAddresses = shippingAddresses;
	}*/
	
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
	
}