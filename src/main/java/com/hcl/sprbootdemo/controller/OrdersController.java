package com.hcl.sprbootdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;

import com.hcl.sprbootdemo.entity.Users;
import com.hcl.sprbootdemo.exception.ResourceNotFoundException;
import com.hcl.sprbootdemo.payload.OrderDTO;
import com.hcl.sprbootdemo.repository.UsersRepository;
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
	@Autowired
	private UsersRepository usersRepo;

	//@Autowired
//	private StripeService stripeService;

	@GetMapping("/hello")
	public String helloFn() {
		return "Order Controller response!";
	}
		
	
	@GetMapping("/admin/getallorders")
    public List<OrderDTO> getAllOrders() {
        return ordersService.getAllOrders();
    }


	@GetMapping("/userrelated/{userId}")
	public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Long userId) {
	    Users user = usersRepo.findById(userId)
	        .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        System.out.println("###########################Get Orders by User ID #############################");
	    List<OrderDTO> orders = ordersService.getOrdersByEmail(user.getEmail());
	    System.out.println(orders);
	    return ResponseEntity.ok(orders);
	}

    @PutMapping("/update/{orderId}")
    public OrderDTO updateOrder(
            @PathVariable Long orderId,
            @RequestBody OrderDTO updatedOrder
    ) {
        return ordersService.updateOrder(orderId, updatedOrder);
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
