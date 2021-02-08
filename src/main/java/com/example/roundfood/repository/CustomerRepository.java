package com.example.roundfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roundfood.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	Customer getOne(Long id);
	
	Customer findByEmail(String email);
	
}
