package com.example.roundfood.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roundfood.model.PaymentOption;
import com.example.roundfood.repository.PaymentOptionRepository;

@Service
public class PaymentOptionDAO {

	@Autowired
	PaymentOptionRepository paymentOptionRepository;
	
	public List<PaymentOption> getAllPaymentOptions(){
		
		return paymentOptionRepository.findAll();
	}
	
	public PaymentOption getPaymentOptionById(Long id) {
		
		return paymentOptionRepository.findById(id).get();
	}
	
}
