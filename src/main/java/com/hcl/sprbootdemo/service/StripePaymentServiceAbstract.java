package com.hcl.sprbootdemo.service;

import java.util.List;

import com.hcl.sprbootdemo.payload.PaymentDTO;
import com.hcl.sprbootdemo.payload.StripePaymentDto;
import com.stripe.exception.StripeException;

public abstract class StripePaymentServiceAbstract {

    /**
     * Create a Stripe payment for a specific order and save it to the database
     */
    public abstract PaymentDTO createStripePayment(StripePaymentDto stripePaymentDto, Long orderId) throws StripeException;

    /**
     * Retrieve all payments
     */
    public abstract List<PaymentDTO> getAllPayments();
    
    /**
     * Low-level method to create a Stripe PaymentIntent.
     * Implementations should call Stripe API here.
     */
    protected abstract com.stripe.model.PaymentIntent createPaymentIntent(StripePaymentDto stripePaymentDto) throws StripeException;
}
