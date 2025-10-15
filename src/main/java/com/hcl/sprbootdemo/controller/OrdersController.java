package com.hcl.sprbootdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;

import com.hcl.sprbootdemo.payload.OrderDTO;
import com.hcl.sprbootdemo.service.OrdersService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;

//    @Autowired
//    private AuthUtil authUtil;

	//@Autowired
//	private StripeService stripeService;

	@GetMapping("/hello")
	public String helloFn() {
		return "Order Controller response!";
	}
	
	@GetMapping("/user/orders")
	public ResponseEntity<List<OrderDTO>> getUserOrders(Authentication authentication) {
	    String email = authentication.getName();  // Gets logged-in user’s email
	    List<OrderDTO> orders = ordersService.getOrdersByEmail(email);
	    return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	/*
	 * @PostMapping("/order/users/payments/{paymentMethod}") public
	 * ResponseEntity<OrderDTO> orderProducts(@PathVariable String
	 * paymentMethod, @RequestBody OrderRequestDTO orderRequestDTO) { // String
	 * emailId = authUtil.loggedInEmail(); String emailId = "dee@gmail.com";
	 * System.out.println("orderRequestDTO DATA: " + orderRequestDTO); OrderDTO
	 * order = ordersService.placeOrder(emailId, orderRequestDTO.getAddress(),
	 * paymentMethod, orderRequestDTO.getPgName(), orderRequestDTO.getPgPaymentId(),
	 * orderRequestDTO.getPgStatus(), orderRequestDTO.getPgResponseMessage() );
	 * return new ResponseEntity<>(order, HttpStatus.CREATED); }
	 * 
	 * @PostMapping("/order/stripe-client-secret") public ResponseEntity<String>
	 * createStripeClientSecret(@RequestBody StripePaymentDto stripePaymentDto)
	 * throws StripeException { System.out.println("StripePaymentDTO Received " +
	 * stripePaymentDto); PaymentIntent paymentIntent =
	 * stripeService.paymentIntent(stripePaymentDto); return new
	 * ResponseEntity<>(paymentIntent.getClientSecret(), HttpStatus.CREATED); }
	 * 
	 * @GetMapping("/admin/orders") public ResponseEntity<OrderResponse>
	 * getAllOrders(
	 * 
	 * @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,
	 * required = false) Integer pageNumber,
	 * 
	 * @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE,
	 * required = false) Integer pageSize,
	 * 
	 * @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_ORDERS_BY,
	 * required = false) String sortBy,
	 * 
	 * @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR,
	 * required = false) String sortOrder ) { OrderResponse orderResponse =
	 * orderService.getAllOrders(pageNumber, pageSize, sortBy, sortOrder); return
	 * new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.OK); }
	 * 
	 * // @GetMapping("/seller/orders") // public ResponseEntity<OrderResponse>
	 * getAllSellerOrders( // @RequestParam(name = "pageNumber", defaultValue =
	 * AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
	 * // @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE,
	 * required = false) Integer pageSize, // @RequestParam(name = "sortBy",
	 * defaultValue = AppConstants.SORT_ORDERS_BY, required = false) String sortBy,
	 * // @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR,
	 * required = false) String sortOrder // ) { // // OrderResponse orderResponse =
	 * orderService.getAllSellerOrders(pageNumber, pageSize, sortBy, sortOrder); //
	 * return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.OK); // }
	 * 
	 * @PutMapping("/admin/orders/{orderId}/status") public ResponseEntity<OrderDTO>
	 * updateOrderStatus(@PathVariable Long orderId,
	 * 
	 * @RequestBody OrderStatusUpdateDTO orderStatusUpdateDto) { OrderDTO order =
	 * orderService.updateOrder(orderId, orderStatusUpdateDto.getStatus()); return
	 * new ResponseEntity<OrderDTO>(order, HttpStatus.OK); }
	 * 
	 * @PutMapping("/seller/orders/{orderId}/status") public
	 * ResponseEntity<OrderDTO> updateOrderStatusSeller(@PathVariable Long orderId,
	 * 
	 * @RequestBody OrderStatusUpdateDTO orderStatusUpdateDto) { OrderDTO order =
	 * orderService.updateOrder(orderId, orderStatusUpdateDto.getStatus()); return
	 * new ResponseEntity<OrderDTO>(order, HttpStatus.OK); }
	 * 
	 
	 */
	}
