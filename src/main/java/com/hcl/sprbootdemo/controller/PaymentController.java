package com.hcl.sprbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hcl.sprbootdemo.payload.PaymentDTO;
import com.hcl.sprbootdemo.payload.StripePaymentDto;
import com.hcl.sprbootdemo.service.StripePaymentServiceAbstract;
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private StripePaymentServiceAbstract stripePaymentServiceAbstract;

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllPayments() {
        return ResponseEntity.ok(stripePaymentServiceAbstract.getAllPayments());
    }

    // Pass orderId in request
    @PostMapping("/stripe/test/{orderId}")
    public ResponseEntity<?> createStripeTestPayment(@PathVariable Long orderId,
                                                     @RequestBody StripePaymentDto stripePaymentDto) {
        try {
            PaymentDTO paymentDTO = stripePaymentServiceAbstract.createStripePayment(stripePaymentDto, orderId);
            return ResponseEntity.ok(paymentDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Payment failed: " + e.getMessage());
        }
    }
}