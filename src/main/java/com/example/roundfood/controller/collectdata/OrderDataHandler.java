package com.example.roundfood.controller.collectdata;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.roundfood.DAO.OrderDAO;
import com.example.roundfood.model.Customer;
import com.example.roundfood.model.ExtraTopping;
import com.example.roundfood.model.Order;
import com.example.roundfood.model.OrderLineItem;
import com.example.roundfood.model.OrderStatus;
import com.example.roundfood.model.PaymentOption;

@Service
public class OrderDataHandler {

	Logger logger = LoggerFactory.getLogger(OrderDataHandler.class);
	
	OrderDAO orderDAO;
	
	OrderLineItemDataHandler orderLineItemDataHandler;
	
	PaymentOptionDataHandler paymentOptionDataHandler;
	
	public OrderDataHandler(OrderDAO orderDAO, OrderLineItemDataHandler orderLineItemDataHandler, PaymentOptionDataHandler paymentOptionDataHandler) {
		this.orderDAO = orderDAO;
		this.orderLineItemDataHandler = orderLineItemDataHandler;
		this.paymentOptionDataHandler = paymentOptionDataHandler;
	}
	
	public Order getOpenedOrderByCustomer(Customer customer) {
		return orderDAO.getOpenedOrderByCustomer(customer);
	}
	
	public Order getOrderById(Long orderId) {
		Order order = orderDAO.getOrderByOrderId(orderId);
		return order;
	}
	
	public boolean updateOrder(Order order) {
		boolean succeeded = false;
		
		try {
			orderDAO.updateOrder(order);
			succeeded = true;
		}
		catch (Exception e) {
			logger.error("Updating order failed: " + e.getMessage());
		}
		
		return succeeded;
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
	
	public boolean finalizeOrder(Long orderId, Date selectedOrderDateAndTimeDate, Long selectedPaymentOptionId) {
		boolean savingSucceeded = false;
		
		PaymentOption paymentOption = paymentOptionDataHandler.getPaymentOptionById(selectedPaymentOptionId);
		
		Order order = getOrderById(orderId);
		
		order.setStatus(OrderStatus.FINALIZED);
		order.setShippingDateAndTime(selectedOrderDateAndTimeDate);
		order.setPaymentOption(paymentOption);
		savingSucceeded = updateOrder(order);
		
		if (savingSucceeded) {
			logger.info("ORDER IS FINALIZED (ID " + String.valueOf(order.getId()) + ")");
		}
		
		return savingSucceeded;
	}
	
	public List<Order> getFinalizedOrdersByCustomer(Customer customer){
		return orderDAO.getFinalizedOrdersByCustomer(customer);
	}
	
	public Map<String, Object> reorderByOrderId(Long reorderedOrderId, Long openedorderId) {
		Map<String, Object> megaPack = new HashMap<>();
		
		boolean succeeded = false;
		Map<String, String> responseMap = new HashMap<>();
		
		try {
			Order originalOrder = getOrderById(reorderedOrderId);
			Long customerId = originalOrder.getCustomer().getId();
			
			for (OrderLineItem lineItem : originalOrder.getOrderLineItems()) {
				Long foodId = lineItem.getFood().getId();
				List<String> selectedToppingIds = new ArrayList<>();
				
				if (!lineItem.getSelectedExtraToppings().isEmpty()) {
					for (ExtraTopping extraTopping : lineItem.getSelectedExtraToppings()) {
						selectedToppingIds.add(String.valueOf(extraTopping.getId()));
					}
				}
				
				responseMap = orderLineItemDataHandler.createNewOrderLineItem(customerId, openedorderId, foodId, selectedToppingIds);
				openedorderId = Long.parseLong(responseMap.get("orderId"));
			}
			
			succeeded = true;
		} catch (Exception e) {
			logger.error("Reordering failed: " + e.getMessage());
		}
		
		megaPack.put("succeeded", succeeded);
		megaPack.put("responseMap", responseMap);
		
		return megaPack;
	}
}
