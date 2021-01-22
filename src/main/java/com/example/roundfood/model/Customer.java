package com.example.roundfood.model;

import javax.persistence.*;
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
    
    private String password;
    
    @Enumerated(EnumType.STRING)
    private CustomerLegitimacy legitimacy;
    
    /*
    @OneToMany(mappedBy = "customer")
    private List<Order> order;
    */

    @OneToMany(mappedBy = "customer")
    private List<ShippingAddress> shippingAddress;

	public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String phoneNumber, String password, CustomerLegitimacy legitimacy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.legitimacy = legitimacy;
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

    /*
    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }*/
    
    public List<ShippingAddress> getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(List<ShippingAddress> shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
    
}