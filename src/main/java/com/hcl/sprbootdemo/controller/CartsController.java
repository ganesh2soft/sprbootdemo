package com.hcl.sprbootdemo.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hcl.sprbootdemo.exception.ResourceNotFoundException;
import com.hcl.sprbootdemo.payload.AddToCartRequest;
import com.hcl.sprbootdemo.payload.CartsDTO;
import com.hcl.sprbootdemo.payload.MessageResponse;
import com.hcl.sprbootdemo.service.CartsService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartsController {
	private static final Logger logger = LoggerFactory.getLogger(CartsController.class);

	@Autowired
	CartsService cartsService;
	@GetMapping("/hello")
	public String helloFn() {
		return "Cart Controller response!";
	}
	@PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request, Principal principal) {
        String userEmail = principal.getName();
           

        try {
            cartsService.addToCart(userEmail, request.getProductId(), request.getQuantity());
            return ResponseEntity.ok("Product added to cart successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add product to cart");
        }
    }

    @GetMapping("/getcarts")
    public ResponseEntity<List<CartsDTO>> getCarts() {
        List<CartsDTO> cartDTOs = cartsService.getAllCarts();
        return new ResponseEntity<>(cartDTOs, HttpStatus.OK);
    }

    @GetMapping("/userrelated/cart/{email}")
    public ResponseEntity<CartsDTO> getCartByemail(@PathVariable String email) {
    	logger.info("At Controller, received input from React "+email);
    	CartsDTO cartDTO = cartsService.getCart(email);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartsDTO> updateProductQuantityInCart(@PathVariable Long productId, @PathVariable String operation) {
        CartsDTO cartDTO = cartsService.updateProductQuantityInCart(productId, operation.equalsIgnoreCase("delete") ? -1 : 1);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/product/{productId}")
    public ResponseEntity<MessageResponse> deleteProductFromCart(@PathVariable Long cartId,
                                                                 @PathVariable Long productId) {
        String status = cartsService.deleteProductFromCart(cartId, productId);
        MessageResponse messageResponse = new MessageResponse(status);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
    
    @DeleteMapping("/userrelated/cart/{email}/products")
    public ResponseEntity<MessageResponse> deleteProductsFromUserCart(
            @PathVariable String email,
            @RequestBody List<Long> productIds) {
        
        logger.info("Cart deletion for user: " + email);
        logger.info("productIds: " + productIds);
        
        try {
            cartsService.deleteProductsFromUserCart(email, productIds);
            return ResponseEntity.ok(new MessageResponse("Selected products removed from cart successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new MessageResponse("Failed to remove products from cart"));
        }
    }

}

