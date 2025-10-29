package com.hcl.sprbootdemo.payload;

import java.util.Date;

public class PaymentDTO {

    private Long paymentId;
    private String paymentMethod;        // e.g., "Card", "UPI", etc.
    private String paymentIntentId;          // Payment Gateway ID (Stripe PaymentIntent ID)
    private String paymentGateway;               // Payment Gateway Name (Stripe)
    private String pgResponseMessage;    // e.g., Stripe status
    private String status;             // Success / Failed
    private String description;
    private Double amount;
    private String currency;
    private String customerName;
    private String customerEmail;
    private Long orderId;
    private Date createdAt; 

    // Getters and Setters

    public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway= paymentGateway;
    }

    public String getPgResponseMessage() {
        return pgResponseMessage;
    }

    public void setPgResponseMessage(String pgResponseMessage) {
        this.pgResponseMessage = pgResponseMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

	@Override
	public String toString() {
		return "PaymentDTO [paymentId=" + paymentId + ", paymentMethod=" + paymentMethod + ", paymentIntentId="
				+ paymentIntentId + ", paymentGateway=" + paymentGateway + ", pgResponseMessage=" + pgResponseMessage
				+ ", status=" + status + ", description=" + description + ", amount=" + amount + ", currency="
				+ currency + ", customerName=" + customerName + ", customerEmail=" + customerEmail + ", orderId="
				+ orderId + ", createdAt=" + createdAt + "]";
	}
    
}
