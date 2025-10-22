package com.hcl.sprbootdemo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

		if (carts.size() == 0) {
			throw new APIException("No cart exists");
		}

		List<CartsDTO> cartDTOs = carts.stream().map(cart -> {
			CartsDTO cartDTO = modelMapper.map(cart, CartsDTO.class);

			List<ProductDTO> products = cart.getCartItems().stream()
					.map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

			cartDTO.setProducts(products);

			return cartDTO;

		}).collect(Collectors.toList());

		return cartDTOs;
	}
	
	@Transactional
	public void addToCart(String userEmail, Long productId, int quantity) {
		logger.info("Received from react, Product ID {}",productId);
		logger.info("Received from react, Quantity are {}",quantity);
	    // 1. Find user by email using Optional
	    Users user = usersRepository.findByEmail(userEmail)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

	    // 2. Find or create cart for user
	    Carts cart = cartsRepository.findByUserEmail(userEmail)
	            .orElseGet(() -> {
	                Carts newCart = new Carts();
	                newCart.setUser(user);
	                Carts savedCart = cartsRepository.save(newCart);
	                logger.info("Cart created for user after save: {}" , savedCart);
	                return savedCart;
	            });

	    // 2.1 Reload the cart to make sure it's managed and fully loaded
	    cart = cartsRepository.findById(cart.getCartId())
	            .orElseThrow(() -> new ResourceNotFoundException("Cart not found after creation"));
	    

	    // 3. Find product by ID
	    Products product = productsRepository.findById(productId)
	            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
	    
	    // 4. Check if product already exists in cart
	    Optional<CartItem> existingCartItemOpt = cart.getCartItems().stream()
	            .filter(item -> item.getProduct().getProductId().equals(productId))
	            .findFirst();
	    
	    if (existingCartItemOpt.isPresent()) {
	        // Update quantity
	    	 
	        CartItem existingCartItem = existingCartItemOpt.get();
	        existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
	        cartItemRepository.save(existingCartItem);
	    } else {
	        // Add new cart item
	        CartItem newCartItem = new CartItem();
	        
	        newCartItem.setCart(cart); // cart has ID and is managed
	        newCartItem.setProduct(product);
	        newCartItem.setQuantity(quantity);
	        cartItemRepository.save(newCartItem);

	        // Also add it to cart's internal list
	        cart.getCartItems().add(newCartItem);
	    }

	    // 5. Save the cart again to persist changes to cartItems list if necessary
	    cartsRepository.save(cart);

	    // 6. Optional: log updated cart for debugging
	    logger.info("Cart after adding item: {} ",cart);
	}

	
	@Override
	public CartsDTO getCart(String email) {
		
		Carts cart = cartsRepository.findCartByEmail(email);
		
		if (cart == null) {
			throw new ResourceNotFoundException("Cart not found for email: " + email);
		}

		// 3. Map cart to DTO
		CartsDTO cartDTO = modelMapper.map(cart, CartsDTO.class);

		// 4. Set product quantity explicitly if needed
		cart.getCartItems().forEach(cartItem -> {
			cartItem.getProduct().setQuantity(cartItem.getQuantity());
		});

		// 5. Map products from cart items
		List<ProductDTO> products = cart.getCartItems().stream()
				.map(item -> modelMapper.map(item.getProduct(), ProductDTO.class)).toList();

		cartDTO.setProducts(products);
		

		return cartDTO;
	}
	
	@Transactional
	@Override
	public String deleteProductFromCart(Long cartId, Long productId) {
		Carts cart = cartsRepository.findById(cartId).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Cart not found with ID: %d", cartId), cartId));

		CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

		if (cartItem == null) {
			throw new ResourceNotFoundException(String.format("Product not found with ID: %d", productId), productId);
		}

		cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));

		cartItemRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

		return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
	}	
	
	@Transactional
	@Override
	public CartsDTO updateProductQuantityInCart(Long productId, Integer quantity) {

		String emailId = authUtil.loggedInEmail();
		Carts userCart = cartsRepository.findCartByEmail(emailId);
		Long cartId = userCart.getCartId();

		Carts cart = cartsRepository.findById(cartId).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Cart not found with ID: %d", cartId), cartId));

		Products product = productsRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Product not found with ID: %d", productId), productId));

		if (product.getQuantity() == 0) {
			throw new APIException(product.getProductName() + " is not available");
		}

		if (product.getQuantity() < quantity) {
			throw new APIException("Please, make an order of the " + product.getProductName()
					+ " less than or equal to the quantity " + product.getQuantity() + ".");
		}

		CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

		if (cartItem == null) {
			throw new APIException("Product " + product.getProductName() + " not available in the cart!!!");
		}

		// Calculate new quantity
		int newQuantity = cartItem.getQuantity() + quantity;

		// Validation to prevent negative quantities
		if (newQuantity < 0) {
			throw new APIException("The resulting quantity cannot be negative.");
		}

		if (newQuantity == 0) {
			deleteProductFromCart(cartId, productId);
		} else {
			cartItem.setProductPrice(product.getSpecialPrice());
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
			cartItem.setDiscount(product.getDiscount());
			cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getProductPrice() * quantity));
			cartsRepository.save(cart);
		}

		CartItem updatedItem = cartItemRepository.save(cartItem);
		if (updatedItem.getQuantity() == 0) {
			cartItemRepository.deleteById(updatedItem.getCartItemId());
		}

		CartsDTO cartDTO = modelMapper.map(cart, CartsDTO.class);

		List<CartItem> cartItems = cart.getCartItems();

		Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
			ProductDTO prd = modelMapper.map(item.getProduct(), ProductDTO.class);
			prd.setQuantity(item.getQuantity());
			return prd;
		});

		cartDTO.setProducts(productStream.toList());

		return cartDTO;
	}
			

	@Override
	@Transactional
	public void deleteProductsFromUserCart(String email, List<Long> productIds) {
	    // Fetch the user's cart
	    Carts cart = cartsRepository.findByUserEmail(email)
	            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + email));

	    // Remove CartItems associated with the specified product IDs
	    cart.getCartItems().removeIf(cartItem -> 
	        productIds.contains(cartItem.getProduct().getProductId())
	    );

	    // Recalculate the total price after removal
	    cart.setTotalPrice(cart.getCartItems().stream()
	            .mapToDouble(cartItem -> cartItem.getProductPrice() * cartItem.getQuantity())
	            .sum());

	    // Save the updated cart
	    cartsRepository.save(cart);
	}
	
	/*
	 * private Carts createCart() { Carts userCart =
	 * cartsRepository.findCartByEmail(authUtil.loggedInEmail()); if (userCart !=
	 * null) { return userCart; } Carts cart = new Carts();
	 * cart.setTotalPrice(0.00); cart.setUser(authUtil.loggedInUser()); Carts
	 * newCart = cartsRepository.save(cart);
	 * logger.info("New cart created in Createcart {}" , newCart); return newCart; }
	 */

}
