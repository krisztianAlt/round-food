package com.example.roundfood.controller.collectdata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.roundfood.DAO.OrderDAO;
import com.example.roundfood.DAO.OrderLineItemDAO;
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.ExtraTopping;
import com.example.roundfood.model.Order;
import com.example.roundfood.model.OrderLineItem;

@Service
public class OrderDataHandler {

	Logger logger = LoggerFactory.getLogger(OrderDataHandler.class);
	
	OrderDAO orderDAO;
	
	OrderLineItemDataHandler orderLineItemDataHandler;
	
	public OrderDataHandler(OrderDAO orderDAO, OrderLineItemDataHandler orderLineItemDataHandler) {
		this.orderDAO = orderDAO;
		this.orderLineItemDataHandler = orderLineItemDataHandler;
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
	
	public boolean deleteOrderAndLineItemsByOrderId(Long id) {
		boolean succeeded = false;
		
		try {
			Order order = orderDAO.getOrderByOrderId(id);
			List<OrderLineItem> orderLineItems = order.getOrderLineItems();
			int size = orderLineItems.size();

			while (size > 0) {
				OrderLineItem orderLineItem = orderLineItems.get(size-1);
	            orderLineItemDataHandler.deleteOrderLineItem(orderLineItem);
	            size--;
			}
			updateOrder(order);
			orderDAO.deleteOrder(order);
			logger.info("ORDER DELETED (ID " + String.valueOf(id) + ")");
			succeeded = true;
		}
		catch (Exception e) {
			logger.error("Order deletion failed in orderDataHandler: " + e.getMessage());
		}

		return succeeded;
	}
}
