package com.example.roundfood.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roundfood.model.OrderLineItem;
import com.example.roundfood.repository.OrderLineItemRepository;

@Service
public class OrderLineItemDAO {

	@Autowired
	OrderLineItemRepository orderLineItemRepository;
	
	
	public OrderLineItem createNewOrderLineItem() {
		return new OrderLineItem();
	}
	
	public void saveNewOrderLineItem(OrderLineItem orderLineItem) {
		orderLineItemRepository.save(orderLineItem);
	}
	
	public boolean deleteOrderLineItem(OrderLineItem orderLineItem) {
		boolean succeeded = false;
		
		try{
			orderLineItemRepository.delete(orderLineItem);
			succeeded = true;
		} catch (Exception e){
			System.out.println("Deletion failed in orderLineItemDAO: " + e.getMessage());
		}
	
		return succeeded;
	}
	
	public OrderLineItem getOrderLineItemById (Long id) {
		return orderLineItemRepository.findById(id).get();
	}
}
