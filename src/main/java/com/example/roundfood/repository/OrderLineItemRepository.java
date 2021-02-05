package com.example.roundfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roundfood.model.OrderLineItem;

public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Long> {

}
