package com.hcl.sprbootdemo.service;

import com.hcl.sprbootdemo.payload.OrderDTO;
import java.util.List;

public interface OrdersService {
    List<OrderDTO> getAllOrders();
    List<OrderDTO> getOrdersByEmail(String email);
    OrderDTO createOrder(String email, OrderDTO orderDTO);
    OrderDTO updateOrder(Long orderId, OrderDTO updatedOrder);
}
