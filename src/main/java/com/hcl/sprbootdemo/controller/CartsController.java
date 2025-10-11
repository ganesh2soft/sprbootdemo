package com.hcl.sprbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hcl.sprbootdemo.entity.Carts;
import com.hcl.sprbootdemo.payload.CartDTO;
import com.hcl.sprbootdemo.payload.CartItemDTO;
import com.hcl.sprbootdemo.repository.CartsRepository;
import com.hcl.sprbootdemo.service.CartsService;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartsController {

	@Autowired
	private CartsRepository cartsRepository;

//    @Autowired
//    private AuthUtil authUtil;

	@Autowired
	private CartsService cartsService;

	@GetMapping("/hello")
	public String helloFn() {
		return "Cart Controller response!";
	}

	/*
	 * @PostMapping("/cart/create") public ResponseEntity<String>
	 * createOrUpdateCart(@RequestBody List<CartItemDTO> cartItems){ String response
	 * = cartService.createOrUpdateCartWithItems(cartItems); return new
	 * ResponseEntity<>(response, HttpStatus.CREATED); }
	 * 
	 * @PostMapping("/carts/products/{productId}/quantity/{quantity}") public
	 * ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
	 * 
	 * @PathVariable Integer quantity){ CartDTO cartDTO =
	 * cartService.addProductToCart(productId, quantity); return new
	 * ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED); }
	 * 
	 * @GetMapping("/carts") public ResponseEntity<List<CartDTO>> getCarts() {
	 * List<CartDTO> cartDTOs = cartService.getAllCarts(); return new
	 * ResponseEntity<List<CartDTO>>(cartDTOs, HttpStatus.FOUND); }
	 * 
	 * @GetMapping("/carts/users/cart") public ResponseEntity<CartDTO>
	 * getCartById(){ // String emailId = authUtil.loggedInEmail(); String emailId =
	 * "dee@gmail.com"; Cart cart = cartRepository.findCartByEmail(emailId); Long
	 * cartId = cart.getId(); CartDTO cartDTO = cartService.getCart(emailId,
	 * cartId); return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK); }
	 * 
	 * @PutMapping("/cart/products/{productId}/quantity/{operation}") public
	 * ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId,
	 * 
	 * @PathVariable String operation) {
	 * 
	 * CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,
	 * operation.equalsIgnoreCase("delete") ? -1 : 1);
	 * 
	 * return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK); }
	 * 
	 * @DeleteMapping("/carts/{cartId}/product/{productId}") public
	 * ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,
	 * 
	 * @PathVariable Long productId) { String status =
	 * cartService.deleteProductFromCart(cartId, productId);
	 * 
	 * return new ResponseEntity<String>(status, HttpStatus.OK); }
	 */

}
