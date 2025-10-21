package com.hcl.sprbootdemo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.sprbootdemo.entity.Orders;
import com.hcl.sprbootdemo.payload.OrderDTO;
import com.hcl.sprbootdemo.repository.CartsRepository;

import com.hcl.sprbootdemo.repository.OrderItemRepository;
import com.hcl.sprbootdemo.repository.OrdersRepository;
import com.hcl.sprbootdemo.repository.PaymentRepository;
import com.hcl.sprbootdemo.repository.ProductsRepository;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	CartsRepository cartsRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	OrdersRepository ordersRepository;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	CartsService cartService;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	ProductsRepository productRepository;

	@Override
	public List<OrderDTO> getAllOrders() {
	    List<Orders> orders = ordersRepository.findAll();
	    return orders.stream()
	                 .map(order -> modelMapper.map(order, OrderDTO.class))
	                 .toList(); // Cleaner and more idiomatic in modern Java
	}


	@Override
	public List<OrderDTO> getOrdersByEmail(String email) {
		List<Orders> orders = ordersRepository.findByEmail(email);
		System.out.println("Received email at order by email" + email);
		return orders.stream()
				.map(order -> modelMapper.map(order, OrderDTO.class))
				.toList();
	}

	@Override
	public OrderDTO updateOrder(Long orderId, OrderDTO updatedOrderDTO) {
		Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		order.setOrderStatus(updatedOrderDTO.getOrderStatus());
		order.setAddress(updatedOrderDTO.getAddress());
		order.setTotalAmount(updatedOrderDTO.getTotalAmount());
		order.setOrderDate(updatedOrderDTO.getOrderDate());
		Orders updated = ordersRepository.save(order);
		return modelMapper.map(updated, OrderDTO.class);
	}
}
