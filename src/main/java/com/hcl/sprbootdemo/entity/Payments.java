package com.hcl.sprbootdemo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne(mappedBy = "payment", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Orders order;

    @NotBlank
    @Size(min = 4, message = "Payment method must contain at least 4 characters")
    private String paymentMethod;
	
    private String pgPaymentId;
    private String pgStatus;
    private String pgResponseMessage;

    private String pgName;


    public Payments(String paymentMethod, String pgPaymentId, String pgStatus,
                   String pgResponseMessage, String pgName) {
        this.paymentMethod = paymentMethod;
        this.pgPaymentId = pgPaymentId;
        this.pgStatus = pgStatus;
        this.pgResponseMessage = pgResponseMessage;
        this.pgName = pgName;
    }


	public Long getPaymentId() {
		return paymentId;
	}


	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}


	public Orders getOrder() {
		return order;
	}


	public void setOrder(Orders order) {
		this.order = order;
	}


	public String getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public String getPgPaymentId() {
		return pgPaymentId;
	}


	public void setPgPaymentId(String pgPaymentId) {
		this.pgPaymentId = pgPaymentId;
	}


	public String getPgStatus() {
		return pgStatus;
	}


	public void setPgStatus(String pgStatus) {
		this.pgStatus = pgStatus;
	}


	public String getPgResponseMessage() {
		return pgResponseMessage;
	}


	public void setPgResponseMessage(String pgResponseMessage) {
		this.pgResponseMessage = pgResponseMessage;
	}


	public String getPgName() {
		return pgName;
	}


	public void setPgName(String pgName) {
		this.pgName = pgName;
	}


	@Override
	public String toString() {
		return "Payments [paymentId=" + paymentId + ", order=" + order + ", paymentMethod=" + paymentMethod
				+ ", pgPaymentId=" + pgPaymentId + ", pgStatus=" + pgStatus + ", pgResponseMessage=" + pgResponseMessage
				+ ", pgName=" + pgName + "]";
	}


	public Payments() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}