package com.hcl.sprbootdemo.service;

import java.util.List;

import com.hcl.sprbootdemo.payload.OrderDTO;

public interface OrdersService {  
	public List<OrderDTO> getAllOrders();
	
	List<OrderDTO> getOrdersByEmail(String email);
	
	OrderDTO updateOrder(Long orderId, OrderDTO updatedOrder);
}
