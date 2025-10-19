package com.hcl.sprbootdemo.service;

import java.util.List;

import com.hcl.sprbootdemo.payload.PaymentDTO;

public interface PaymentService {
	List<PaymentDTO> getAllPayments();
	void payForCart(String email);

}
