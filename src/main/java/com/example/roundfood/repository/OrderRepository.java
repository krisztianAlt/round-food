package com.example.roundfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roundfood.model.Customer;
import com.example.roundfood.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> getOrdersByCustomer(Customer customer);
	
	Order findFirstByCustomerOrderByOrderingTimeStampDesc (Customer customer);
}
