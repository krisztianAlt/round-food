package com.example.roundfood.controller.collectdata;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
		
		if (openedorderId == null) {
			order = orderDAO.createNewOrder();
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
		
		responseMap.put("orderId", String.valueOf(order.getId()));
		responseMap.put("numberOfOrderItems", String.valueOf(order.getOrderLineItems().size()));
		
		return responseMap;
	}
	
}
