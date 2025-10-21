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
	
	}
