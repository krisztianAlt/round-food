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
	
	public boolean deleteOrderLineItem(Long orderLineItemId) {
		boolean succeeded = false;
		
		try{
            orderLineItemRepository.deleteById(orderLineItemId);
            succeeded = true;
        } catch (Exception e){
            System.out.println("Deletion failed: " + e.getMessage());
        }
		
		return succeeded;
	}
	
}
