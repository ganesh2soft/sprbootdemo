package com.hcl.sprbootdemo.service;

import com.hcl.sprbootdemo.payload.StripePaymentDto;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface StripeService {
	PaymentIntent paymentIntent(StripePaymentDto stripePaymentDto) throws StripeException;
}	
