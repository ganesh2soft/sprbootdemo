package com.hcl.sprbootdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcl.sprbootdemo.entity.CartItem;
import com.hcl.sprbootdemo.entity.Carts;
import com.hcl.sprbootdemo.entity.Products;
import com.hcl.sprbootdemo.entity.Users;
import com.hcl.sprbootdemo.exception.APIException;
import com.hcl.sprbootdemo.exception.ResourceNotFoundException;
import com.hcl.sprbootdemo.payload.CartItemDTO;
import com.hcl.sprbootdemo.payload.CartsDTO;
import com.hcl.sprbootdemo.payload.ProductDTO;
import com.hcl.sprbootdemo.repository.CartItemRepository;
import com.hcl.sprbootdemo.repository.CartsRepository;
import com.hcl.sprbootdemo.repository.ProductsRepository;
import com.hcl.sprbootdemo.repository.UsersRepository;
import com.hcl.sprbootdemo.security.AuthUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class CartsServiceImpl implements CartsService {
	private static final Logger logger = LoggerFactory.getLogger(CartsServiceImpl.class);

	@Autowired
	private CartsRepository cartsRepository;

	@Autowired
	private ProductsRepository productsRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AuthUtil authUtil;

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public List<CartsDTO> getAllCarts() {
		List<Carts> carts = cartsRepository.findAll();

		if (carts.isEmpty()) {
			throw new APIException("No cart exists");
		}

		return carts.stream().map(cart -> {
			CartsDTO cartDTO = new CartsDTO();
			cartDTO.setCartId(cart.getCartId());
			cartDTO.setUserId(cart.getUser().getUserId());

			// Map each CartItem to CartItemDTO
			List<CartItemDTO> cartItems = cart.getCartItems().stream().map(item -> {
				CartItemDTO itemDTO = new CartItemDTO();
				itemDTO.setCartItemId(item.getCartItemId());
				itemDTO.setPlacedQty(item.getPlacedQty());
				itemDTO.setProduct(modelMapper.map(item.getProduct(), ProductDTO.class));
				return itemDTO;
			}).toList();

			cartDTO.setCartItemDTO(cartItems);

			// Calculate total price dynamically
			double total = cartItems.stream().mapToDouble(ci -> ci.getProduct().getPrice() * ci.getPlacedQty()).sum();
			cartDTO.setTotalPrice(total);

			return cartDTO;
		}).toList();
	}

	@Transactional
	public void addToCart(String userEmail, Long productId, int quantity) {
	    logger.info("Adding Product ID {} with quantity {} for user {}", productId, quantity, userEmail);

	    // 1. Find user
	    Users user = usersRepository.findByEmail(userEmail)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

	    // 2. Find or create cart for user
	    Carts cart = cartsRepository.findByUserEmail(userEmail).orElseGet(() -> {
	        Carts newCart = new Carts();
	        newCart.setUser(user);
	        return cartsRepository.save(newCart);
	    });

	    // 3. Find product
	    Products product = productsRepository.findById(productId)
	            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

	    // 4. Check if product already exists in cart
	    Optional<CartItem> existingCartItemOpt = cart.getCartItems().stream()
	            .filter(item -> item.getProduct().getProductId().equals(productId))
	            .findFirst();

	    if (existingCartItemOpt.isPresent()) {
	        // Update placed quantity
	        CartItem existingCartItem = existingCartItemOpt.get();
	        existingCartItem.setPlacedQty(existingCartItem.getPlacedQty() + quantity);
	        cartItemRepository.save(existingCartItem);
	    } else {
	        // Add new cart item
	        CartItem newCartItem = new CartItem();
	        newCartItem.setCart(cart);
	        newCartItem.setProduct(product);
	        newCartItem.setPlacedQty(quantity);
	        cartItemRepository.save(newCartItem);

	        // Add to cart's internal list
	        cart.getCartItems().add(newCartItem);
	    }

	    // Save cart if needed
	    cartsRepository.save(cart);

	    logger.info("Updated cart: {}", cart);
	}


	@Override
	public CartsDTO getCart(String email) {
	    Carts cart = cartsRepository.findCartByEmail(email);

	    if (cart == null) {
	        throw new ResourceNotFoundException("Cart not found for email: " + email);
	    }

	    // Map cart to DTO
	    CartsDTO cartDTO = modelMapper.map(cart, CartsDTO.class);

	    // Map cart items to CartItemDTO
	    List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream().map(cartItem -> {
	        CartItemDTO itemDTO = new CartItemDTO();
	        itemDTO.setCartItemId(cartItem.getCartItemId());
	        itemDTO.setPlacedQty(cartItem.getPlacedQty());

	        // Map product to ProductDTO inside CartItemDTO
	        ProductDTO productDTO = modelMapper.map(cartItem.getProduct(), ProductDTO.class);
	        itemDTO.setProduct(productDTO);

	        return itemDTO;
	    }).toList();

	    cartDTO.setCartItemDTO(cartItemDTOs);

	    return cartDTO;
	}


	@Transactional
	@Override
	public String deleteProductFromCart(Long cartId, Long productId) {
	    // 1. Find cart
	    Carts cart = cartsRepository.findById(cartId).orElseThrow(
	            () -> new ResourceNotFoundException(
	                    String.format("Cart not found with ID: %d", cartId), cartId));

	    // 2. Find cart item
	    CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);
	    if (cartItem == null) {
	        throw new ResourceNotFoundException(
	                String.format("Product not found with ID: %d", productId), productId);
	    }

	    // 3. Optional: compute price if needed
	    double itemTotal = cartItem.getProduct().getPrice() * cartItem.getPlacedQty();

	    // 4. Remove the cart item
	    cartItemRepository.delete(cartItem);

	    // 5. Optional: recompute cart totalPrice if you store it in Cart
	    // cart.setTotalPrice(cart.getCartItems().stream()
	    //         .mapToDouble(ci -> ci.getProduct().getPrice() * ci.getPlacedQty())
	    //         .sum());
	    // cartsRepository.save(cart);

	    return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
	}

	@Transactional
	@Override
	public CartsDTO updateProductQuantityInCart(Long productId, Integer quantity) {

	    String emailId = authUtil.loggedInEmail();
	    Carts cart = cartsRepository.findCartByEmail(emailId);

	    if (cart == null) {
	        throw new ResourceNotFoundException("Cart not found for email: " + emailId);
	    }

	    Products product = productsRepository.findById(productId)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    String.format("Product not found with ID: %d", productId), productId));

	    if (product.getQuantity() == 0) {
	        throw new APIException(product.getProductName() + " is not available");
	    }

	    if (product.getQuantity() < quantity) {
	        throw new APIException("Please order " + product.getProductName() +
	                " less than or equal to the available quantity: " + product.getQuantity());
	    }

	    CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(), productId);

	    if (cartItem == null) {
	        throw new APIException("Product " + product.getProductName() + " not in the cart!");
	    }

	    int newQuantity = cartItem.getPlacedQty() + quantity;
	    if (newQuantity < 0) {
	        throw new APIException("The resulting quantity cannot be negative.");
	    }

	    if (newQuantity == 0) {
	        deleteProductFromCart(cart.getCartId(), productId);
	    } else {
	        cartItem.setPlacedQty(newQuantity);
	        cartItemRepository.save(cartItem);
	    }

	    // Map cart to DTO
	    CartsDTO cartDTO = modelMapper.map(cart, CartsDTO.class);

	    List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream().map(item -> {
	        CartItemDTO itemDTO = modelMapper.map(item, CartItemDTO.class);
	        ProductDTO productDTO = modelMapper.map(item.getProduct(), ProductDTO.class);
	        productDTO.setQuantity(item.getPlacedQty()); // quantity in cart
	        itemDTO.setProduct(productDTO);
	        return itemDTO;
	    }).toList();

	    cartDTO.setCartItemDTO(cartItemDTOs);

	    return cartDTO;
	}


	@Override
	@Transactional
	public void deleteProductsFromUserCart(String email, List<Long> productIds) {
	    // Fetch the user's cart
	    Carts cart = cartsRepository.findByUserEmail(email)
	            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + email));

	    // Remove CartItems associated with the specified product IDs
	    List<CartItem> itemsToRemove = cart.getCartItems().stream()
	            .filter(cartItem -> productIds.contains(cartItem.getProduct().getProductId()))
	            .toList();

	    cart.getCartItems().removeAll(itemsToRemove);

	    // Delete CartItems from the repository
	    cartItemRepository.deleteAll(itemsToRemove);

	    // No need to update totalPrice on Cart anymore

	    // Save the updated cart (with orphan removal)
	    cartsRepository.save(cart);
	}

}
