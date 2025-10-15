package com.hcl.sprbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hcl.sprbootdemo.payload.CartsDTO;
import com.hcl.sprbootdemo.payload.MessageResponse;
import com.hcl.sprbootdemo.service.CartsService;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartsController {

	@Autowired
	CartsService cartsService;
	@GetMapping("/hello")
	public String helloFn() {
		return "Cart Controller response!";
	}
	
	@PostMapping("/addprod2cart/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartsDTO> addProductToCartfn(@PathVariable Long productId, @PathVariable Integer quantity) {
       
       CartsDTO cartDTO= cartsService.addProductsToCart(productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getcarts")
    public ResponseEntity<List<CartsDTO>> getCarts() {
        List<CartsDTO> cartDTOs = cartsService.getAllCarts();
        return new ResponseEntity<>(cartDTOs, HttpStatus.OK);
    }

    @GetMapping("/users/cart")
    public ResponseEntity<CartsDTO> getCartById() {
        CartsDTO cartDTO = cartsService.getCart();
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
}
