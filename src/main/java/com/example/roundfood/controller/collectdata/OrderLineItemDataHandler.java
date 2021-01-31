package com.example.roundfood.controller.collectdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roundfood.DAO.CustomerDAO;
import com.example.roundfood.DAO.ExtraToppingDAO;
import com.example.roundfood.DAO.FoodDAO;
import com.example.roundfood.DAO.OrderDAO;
import com.example.roundfood.DAO.OrderLineItemDAO;
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.ExtraTopping;
import com.example.roundfood.model.Food;
import com.example.roundfood.model.Order;
import com.example.roundfood.model.OrderLineItem;

@Service
public class OrderLineItemDataHandler {
	
	Logger logger = LoggerFactory.getLogger(OrderLineItemDataHandler.class);
	
	@Autowired
	OrderLineItemDAO orderLineItemDAO;
	
	@Autowired
	OrderDAO orderDAO;
	
	@Autowired
	CustomerDAO customerDAO;
	
	@Autowired
	FoodDAO foodDAO;
	
	@Autowired
	ExtraToppingDAO extraToppingDAO;
	
	public OrderLineItemDataHandler (OrderLineItemDAO orderLineItemDAO) {
		this.orderLineItemDAO = orderLineItemDAO;
	}

	public Map createNewOrderLineItem(Long customerId, Long openedorderId, Long foodId, List<String> selectedToppingIds) {
		Map<String, String> responseMap = new HashMap<>();
		
		Order order;
		
		if (openedorderId == null || (openedorderId != null && !openedOrderIdExists(openedorderId))) {
			order = orderDAO.createNewOrder();
			logger.info("NEW ORDER CREATED (ID: " + String.valueOf(order.getId()) + ")");
			Customer customer = customerDAO.getCustomerById(customerId);
			order.setCustomer(customer);
		} else {
			order = orderDAO.getOrderByOrderId(openedorderId);
		}
		
		Food food = foodDAO.getFoodById(foodId);
		OrderLineItem newOrderLineItem = orderLineItemDAO.createNewOrderLineItem();
		newOrderLineItem.setFood(food);
		newOrderLineItem.setOrder(order);
		
		if (!selectedToppingIds.isEmpty()) {
			List<ExtraTopping> selectedExtraToppings = new ArrayList<>();
			
			for (String id : selectedToppingIds) {
				ExtraTopping extraTopping = extraToppingDAO.getExtraToppingById(Long.parseLong(id));
				selectedExtraToppings.add(extraTopping);
			}
			
			newOrderLineItem.setSelectedExtraToppings(selectedExtraToppings);
		}
		
		if (order.getOrderLineItems() == null) {
			order.setOrderLineItems(new ArrayList<OrderLineItem>());
		}
		
		List<OrderLineItem> orderLineItems = order.getOrderLineItems();
		if (orderLineItems == null) {
			orderLineItems = new ArrayList<OrderLineItem>();
		}
		orderLineItems.add(newOrderLineItem);
		order.setOrderLineItems(orderLineItems);
		orderDAO.updateOrder(order);
		logger.info("NEW LINEITEM ADDED TO ORDER (ID: " + String.valueOf(order.getId()) + ")");
		
		responseMap.put("orderId", String.valueOf(order.getId()));
		responseMap.put("numberOfOrderItems", String.valueOf(order.getOrderLineItems().size()));
		
		return responseMap;
	}
	
	private boolean openedOrderIdExists(Long orderId){
		boolean orderIdExists = false;
		Order order = orderDAO.getOrderByOrderId(orderId);
		
		if (order != null) {
			orderIdExists = true;
		}
		
		return orderIdExists;
	}
	
}
