package com.hcl.sprbootdemo.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.sprbootdemo.entity.Payments;
import com.hcl.sprbootdemo.payload.PaymentDTO;
import com.hcl.sprbootdemo.repository.PaymentRepository;
@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<PaymentDTO> getAllPayments() {
	    List<Payments> payments = paymentRepository.findAll();
	    return payments.stream()
	            .map(payment -> modelMapper.map(payment, PaymentDTO.class))
	            .toList();
	}


}
