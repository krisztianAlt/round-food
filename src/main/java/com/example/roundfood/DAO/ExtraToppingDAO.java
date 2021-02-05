package com.example.roundfood.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roundfood.model.ExtraTopping;
import com.example.roundfood.repository.ExtraToppingRepository;

@Service
public class ExtraToppingDAO {

	@Autowired
	ExtraToppingRepository extraToppingRepository;
	
	public ExtraTopping getExtraToppingById(Long id) {
		return extraToppingRepository.getOne(id);
	}
}
