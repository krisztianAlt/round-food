package com.example.roundfood.controller.collectdata;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.roundfood.DAO.PaymentOptionDAO;
import com.example.roundfood.model.PaymentOption;

@Service
public class PaymentOptionDataHandler {

	private PaymentOptionDAO paymentOptionDAO;
	
	public PaymentOptionDataHandler(PaymentOptionDAO paymentOptionDAO) {
		this.paymentOptionDAO = paymentOptionDAO;
	}
	
	public List<PaymentOption> getAllPaymentOptions(){
		return paymentOptionDAO.getAllPaymentOptions();
	}
	
	public PaymentOption getPaymentOptionById(Long id) {
		return paymentOptionDAO.getPaymentOptionById(id);
	}
}
