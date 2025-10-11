package com.hcl.sprbootdemo.payload;

public class OrderRequestDTO {
	private String address;
	private String paymentMethod;
	private String pgName;
	private String pgPaymentId;
	private String pgStatus;
	private String pgResponseMessage;

	public OrderRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderRequestDTO(String address, String paymentMethod, String pgName, String pgPaymentId, String pgStatus,
			String pgResponseMessage) {
		super();
		this.address = address;
		this.paymentMethod = paymentMethod;
		this.pgName = pgName;
		this.pgPaymentId = pgPaymentId;
		this.pgStatus = pgStatus;
		this.pgResponseMessage = pgResponseMessage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPgName() {
		return pgName;
	}

	public void setPgName(String pgName) {
		this.pgName = pgName;
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

}
