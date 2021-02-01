package com.example.roundfood.controller.collectdata;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.roundfood.DAO.OrderDAO;
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.ExtraTopping;
import com.example.roundfood.model.Order;
import com.example.roundfood.model.OrderLineItem;

@Service
public class OrderDataHandler {

	OrderDAO orderDAO;
	
	public OrderDataHandler(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	
	public Order getOpenedOrderByCustomer(Customer customer) {
		return orderDAO.getOpenedOrderByCustomer(customer);
	}
	
	public Order getOrderById(Long orderId) {
		Order order = orderDAO.getOrderByOrderId(orderId);
		return order;
	}
	
	public void updateOrder(Order order) {
		orderDAO.updateOrder(order);
	}
	
	public double getTotalPrice(Order order) {
		double totalPrice = 0;
        
        for (OrderLineItem orderLineItem : order.getOrderLineItems()) {
        	totalPrice += orderLineItem.getFood().getPrice();
        	for (ExtraTopping extraTopping : orderLineItem.getSelectedExtraToppings()) {
        		totalPrice += extraTopping.getPrice();
        	}
        }
        
        return totalPrice;
	}
	
	public void deleteOrder(Order order) {
		orderDAO.deleteOrder(order);
	}
	
}
