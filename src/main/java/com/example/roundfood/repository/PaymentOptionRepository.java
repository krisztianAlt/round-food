package com.example.roundfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roundfood.model.PaymentOption;

public interface PaymentOptionRepository extends JpaRepository<PaymentOption, Long>{

}
