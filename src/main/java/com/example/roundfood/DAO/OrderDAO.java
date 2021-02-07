package com.example.roundfood.DAO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roundfood.model.Customer;
import com.example.roundfood.model.Order;
import com.example.roundfood.model.OrderStatus;
import com.example.roundfood.repository.OrderRepository;

@Service
public class OrderDAO {

	@Autowired
	OrderRepository orderRepository;
	
	private LocalDateTime localDateTime;
	private Timestamp timestampMaker;
	
	public Order getOrderByOrderId(Long id) {
		Optional<Order> orderOpt = orderRepository.findById(id);
		Order order;
		if (orderOpt.isEmpty()) {
			order = null;
		} else {
			order = orderOpt.get();
		}
		return order;
	}
	
	public Order getOpenedOrderByCustomer(Customer customer) {
		return orderRepository.findFirstByCustomerAndStatusOrderByOrderingTimeStampDesc(customer, OrderStatus.OPENED);
	}
	
	public List<Order> getFinalizedOrdersByCustomer(Customer customer){
		return orderRepository.findByCustomerAndStatusOrderByOrderingTimeStampDesc(customer, OrderStatus.FINALIZED);
	}
	
	public Order createNewOrder() {
		Order newOrder = new Order();
		newOrder.setOrderingTimeStamp(timestampMaker.valueOf(localDateTime.now()));
		newOrder.setStatus(OrderStatus.OPENED);
		orderRepository.save(newOrder);
		return newOrder;
	}
	
	public void updateOrder(Order updatedOrder) {
		Order order = orderRepository.findById(updatedOrder.getId()).get();
		
		order.setCustomer(updatedOrder.getCustomer());
		order.setOrderingTimeStamp(timestampMaker.valueOf(localDateTime.now()));
		order.setOrderLineItems(updatedOrder.getOrderLineItems());
		order.setShippingDateAndTime(updatedOrder.getShippingDateAndTime());
		order.setStatus(updatedOrder.getStatus());
		order.setPaymentOption(updatedOrder.getPaymentOption());
		
		orderRepository.save(order);
	}
	
	public void deleteOrder(Order order) {
		orderRepository.delete(order);
	}
	
}
