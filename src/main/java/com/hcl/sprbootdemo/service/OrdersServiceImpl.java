package com.hcl.sprbootdemo.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hcl.sprbootdemo.entity.Carts;
import com.hcl.sprbootdemo.entity.CartItem;
import com.hcl.sprbootdemo.entity.Orders;
import com.hcl.sprbootdemo.entity.OrderItem;
import com.hcl.sprbootdemo.entity.Payments;
import com.hcl.sprbootdemo.entity.Products;
import com.hcl.sprbootdemo.exception.APIException;
import com.hcl.sprbootdemo.exception.ResourceNotFoundException;
import com.hcl.sprbootdemo.payload.OrderDTO;
import com.hcl.sprbootdemo.payload.OrderResponse;
import com.hcl.sprbootdemo.repository.CartsRepository;

import com.hcl.sprbootdemo.repository.OrderItemRepository;
import com.hcl.sprbootdemo.repository.OrdersRepository;
import com.hcl.sprbootdemo.repository.PaymentRepository;
import com.hcl.sprbootdemo.repository.ProductsRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO updatedOrderDTO) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus(updatedOrderDTO.getOrderStatus());
        order.setAddress(updatedOrderDTO.getAddress());
        order.setTotalAmount(updatedOrderDTO.getTotalAmount());
        order.setOrderDate(updatedOrderDTO.getOrderDate());

        Orders updated = ordersRepository.save(order);
        return modelMapper.map(updated, OrderDTO.class);
    }
    /*
    public List<OrderDTO> getOrdersByEmail(String email) {
        List<Orders> orders = ordersRepository.findByEmail(email);

        return orders.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
/*
    private OrderDTO convertToDTO(Orders order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setEmail(order.getEmail());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setAddress(order.getAddress());
        if (order.getPayment() != null) {
            dto.setPaymentId(order.getPayment().getPaymentId());
        }

        dto.setItems(order.getOrderItems().stream().map(item -> {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setProductName(item.getProduct().getProductName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());
            return itemDTO;
        }).collect(Collectors.toList()));

        return dto;
    }

    
    
//    @Autowired
//    AuthUtil authUtil;
/*
    @Override
    @Transactional
    public OrderDTO placeOrder(String emailId, String address, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage) {
        Cart cart = cartRepository.findCartByEmail(emailId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "email", emailId);
        }

//        Address address = addressRepository.findById(addressId)
//                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        Order order = new Order();
        order.setEmail(emailId);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Accepted");
        order.setAddress(address);

        Payment payment = new Payment(paymentMethod, pgPaymentId, pgStatus, pgResponseMessage, pgName);
        payment.setOrder(order);
        payment = paymentRepository.save(payment);
        order.setPayment(payment);

        Order savedOrder = orderRepository.save(order);

        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new APIException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderedProductPrice(cartItem.getProductPrice());
            orderItem.setOrder(savedOrder);
            orderItems.add(orderItem);
        }

        orderItems = orderItemRepository.saveAll(orderItems);

        cart.getCartItems().forEach(item -> {
            int quantity = item.getQuantity();
            Product product = item.getProduct();

            // Reduce stock quantity
            product.setQuantity(product.getQuantity() - quantity);

            // Save product back to the database
            productRepository.save(product);

            // Remove items from cart
            cartService.deleteProductFromCart(cart.getId(), item.getProduct().getId());
        });

        OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);
       // orderItems.forEach(item -> orderDTO.getOrderItems().add(modelMapper.map(item, OrderItemDTO.class)));
        orderItems.forEach(item -> orderDTO.getOrderItems());
       // orderDTO.setAddressId(addressId);
        orderDTO.setAddress(address);
        return orderDTO;
    }

    @Override
    public OrderResponse getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Order> pageOrders = orderRepository.findAll(pageDetails);
        List<Order> orders = pageOrders.getContent();
        List<OrderDTO> orderDTOs = orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(orderDTOs);
        orderResponse.setPageNumber(pageOrders.getNumber());
        orderResponse.setPageSize(pageOrders.getSize());
        orderResponse.setTotalElements(pageOrders.getTotalElements());
        orderResponse.setTotalPages(pageOrders.getTotalPages());
        orderResponse.setLastPage(pageOrders.isLast());
        return orderResponse;
    }

    @Override
    public OrderDTO updateOrder(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order","orderId",orderId));
        order.setOrderStatus(status);
        orderRepository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }

   /* @Override
    public OrderResponse getAllSellerOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

       // User seller = authUtil.loggedInUser();

        Page<Order> pageOrders = orderRepository.findAll(pageDetails);

        List<Order> sellerOrders = pageOrders.getContent().stream()
                .filter(order -> order.getOrderItems().stream()
                        .anyMatch(orderItem -> {
                            var product = orderItem.getProduct();
                            if (product == null || product.getUser() == null) {
                                return false;
                            }
                            return product.getUser().getUserId().equals(
                                    seller.getUserId());
                        }))
                .toList();

        List<OrderDTO> orderDTOs = sellerOrders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(orderDTOs);
        orderResponse.setPageNumber(pageOrders.getNumber());
        orderResponse.setPageSize(pageOrders.getSize());
        orderResponse.setTotalElements(pageOrders.getTotalElements());
        orderResponse.setTotalPages(pageOrders.getTotalPages());
        orderResponse.setLastPage(pageOrders.isLast());
        return orderResponse;
    }

	
*/

	@Override
	public List<OrderDTO> getOrdersByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
