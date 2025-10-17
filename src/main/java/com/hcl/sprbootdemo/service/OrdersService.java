package com.hcl.sprbootdemo.service;

import java.util.List;

import com.hcl.sprbootdemo.payload.OrderDTO;

public interface OrdersService {
  //  @Transactional
  //  OrderDTO placeOrder(String emailId, String address, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);

 //   OrderResponse getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

 //   OrderDTO updateOrder(Long orderId, String status);

   // OrderResponse getAllSellerOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
	
	public List<OrderDTO> getAllOrders();
	OrderDTO updateOrder(Long orderId, OrderDTO updatedOrder);
	List<OrderDTO> getOrdersByEmail(String email);

}
