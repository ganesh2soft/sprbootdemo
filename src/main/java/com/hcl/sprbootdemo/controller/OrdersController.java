package com.hcl.sprbootdemo.controller;

import com.hcl.sprbootdemo.entity.Users;
import com.hcl.sprbootdemo.exception.ResourceNotFoundException;
import com.hcl.sprbootdemo.payload.OrderDTO;
import com.hcl.sprbootdemo.service.OrdersService;
import com.hcl.sprbootdemo.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private UsersRepository usersRepo;

    @GetMapping("/hello")
    public String helloFn() {
        return "Order Controller response!";
    }

    // ✅ Admin: Fetch all orders
    @GetMapping("/admin/getallorders")
    public List<OrderDTO> getAllOrders() {
        return ordersService.getAllOrders();
    }

    // ✅ User: Get orders by user ID (converted to email)
    @GetMapping("/userrelated/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Long userId) {
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        List<OrderDTO> orders = ordersService.getOrdersByEmail(user.getEmail());
        System.out.println("Orders "+ orders);
        return ResponseEntity.ok(orders);
    }

    // ✅ Create a new order using OrderDTO directly
    @PostMapping("/create/{email}")
    public ResponseEntity<OrderDTO> createOrder(
            @PathVariable String email,
            @RequestBody OrderDTO orderDTO) {

        OrderDTO createdOrder = ordersService.createOrder(email, orderDTO);
        return ResponseEntity.ok(createdOrder);
    }

    // ✅ Update an existing order
    @PutMapping("/update/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable Long orderId,
            @RequestBody OrderDTO updatedOrder) {

        OrderDTO updated = ordersService.updateOrder(orderId, updatedOrder);
        return ResponseEntity.ok(updated);
    }
}
