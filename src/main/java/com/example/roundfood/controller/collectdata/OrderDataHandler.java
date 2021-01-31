package com.example.roundfood.controller.collectdata;

import org.springframework.stereotype.Service;

import com.example.roundfood.DAO.OrderDAO;
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.Order;

@Service
public class OrderDataHandler {

	OrderDAO orderDAO;
	
	public OrderDataHandler(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	
	public Order getOpenedOrderByCustomer(Customer customer) {
		return orderDAO.getOpenedOrderByCustomer(customer);
	}
}
