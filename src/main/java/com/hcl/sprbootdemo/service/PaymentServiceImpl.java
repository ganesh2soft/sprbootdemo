package com.hcl.sprbootdemo.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.sprbootdemo.entity.Carts;
import com.hcl.sprbootdemo.entity.Payments;
import com.hcl.sprbootdemo.exception.APIException;
import com.hcl.sprbootdemo.exception.ResourceNotFoundException;
import com.hcl.sprbootdemo.payload.PaymentDTO;
import com.hcl.sprbootdemo.repository.CartsRepository;
import com.hcl.sprbootdemo.repository.PaymentRepository;

import jakarta.transaction.Transactional;
@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	CartsRepository cartsRepository;

	@Override
	public List<PaymentDTO> getAllPayments() {
	    List<Payments> payments = paymentRepository.findAll();
	    return payments.stream()
	            .map(payment -> modelMapper.map(payment, PaymentDTO.class))
	            .toList();
	}

	@Override
	@Transactional
	public void payForCart(String email) {
	    // Fetch the user's cart
	    Carts cart = cartsRepository.findByUserEmail(email)
	            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + email));

	    // Process the payment (this is a placeholder; integrate with your payment gateway)
	    boolean paymentSuccessful = processPayment(cart);

	    if (paymentSuccessful) {
	        // Remove purchased products from the cart
	        cart.getCartItems().clear(); // orphanRemoval will delete CartItems
	        cartsRepository.save(cart);
	    } else {
	        throw new APIException("Payment processing failed.");
	    }
	}


	private boolean processPayment(Carts cart) {
	    // Integrate with your payment gateway here
	    // For now, we'll assume the payment is always successful
	    return true;
	}

}
