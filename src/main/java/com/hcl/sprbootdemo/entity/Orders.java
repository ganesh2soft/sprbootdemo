package com.hcl.sprbootdemo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")

public class Orders {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long orderId;

	    @Email
	    @Column(nullable = false)
	    private String email;

	    @OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	    private List<OrderItem> orderItems = new ArrayList<>();

	    private LocalDate orderDate;

	    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
	    private Payments payment;

	    private Double totalAmount;
	    private String orderStatus;

	    private String address;

		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public List<OrderItem> getOrderItems() {
			return orderItems;
		}

		public void setOrderItems(List<OrderItem> orderItems) {
			this.orderItems = orderItems;
		}

		public LocalDate getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(LocalDate orderDate) {
			this.orderDate = orderDate;
		}

		public Payments getPayment() {
			return payment;
		}

		public void setPayment(Payments payment) {
			this.payment = payment;
		}

		public Double getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}

		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		@Override
		public String toString() {
			return "Order [orderId=" + orderId + ", email=" + email + ", orderItems=" + orderItems + ", orderDate="
					+ orderDate + ", payment=" + payment + ", totalAmount=" + totalAmount + ", orderStatus="
					+ orderStatus + ", address=" + address + "]";
		}

		public Orders() {
			super();
	
		}

		public Orders(Long orderId, @Email String email, List<OrderItem> orderItems, LocalDate orderDate,
				Payments payment, Double totalAmount, String orderStatus, String address) {
			super();
			this.orderId = orderId;
			this.email = email;
			this.orderItems = orderItems;
			this.orderDate = orderDate;
			this.payment = payment;
			this.totalAmount = totalAmount;
			this.orderStatus = orderStatus;
			this.address = address;
		}
	    
	    
	    

}
