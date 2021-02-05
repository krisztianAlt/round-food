package com.example.roundfood.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
public class Order {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
    @JsonBackReference
    @ManyToOne
    private Customer customer;

    private Timestamp orderingTimeStamp;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date shippingDateAndTime;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderLineItem> orderLineItems;

    @JsonManagedReference
	@ManyToOne
	private PaymentOption paymentOption;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(){}

    public Order(Customer customer, OrderStatus orderStatus) {
        this.customer = customer;
        this.status = orderStatus;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Timestamp getOrderingTimeStamp() {
		return orderingTimeStamp;
	}

	public void setOrderingTimeStamp(Timestamp orderingTimeStamp) {
		this.orderingTimeStamp = orderingTimeStamp;
	}

	public Date getShippingDateAndTime() {
		return shippingDateAndTime;
	}

	public void setShippingDateAndTime(Date shippingDateAndTime) {
		this.shippingDateAndTime = shippingDateAndTime;
	}

	public List<OrderLineItem> getOrderLineItems() {
		return orderLineItems;
	}

	public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	public PaymentOption getPaymentOption() {
		return paymentOption;
	}

	public void setPaymentOption(PaymentOption paymentOption) {
		this.paymentOption = paymentOption;
	}
	
}