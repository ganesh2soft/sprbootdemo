package com.hcl.sprbootdemo.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.sprbootdemo.entity.CartItem;
import com.hcl.sprbootdemo.entity.Carts;
import com.hcl.sprbootdemo.entity.Products;
import com.hcl.sprbootdemo.exception.APIException;
import com.hcl.sprbootdemo.exception.OutOfStockException;
import com.hcl.sprbootdemo.exception.ResourceNotFoundException;
import com.hcl.sprbootdemo.payload.CartsDTO;
import com.hcl.sprbootdemo.payload.ProductDTO;
import com.hcl.sprbootdemo.repository.CartItemRepository;
import com.hcl.sprbootdemo.repository.CartsRepository;
import com.hcl.sprbootdemo.repository.ProductsRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartsServiceImpl implements CartsService {

	@Autowired
	private CartsRepository cartRepository;

	@Autowired
	private ProductsRepository productsRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CartsDTO addProductsToCart(Long productId, Integer quantity) {
        Carts cart = createCart();

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(), productId);

        if (cartItem != null) {
            throw new APIException("Product " + product.getProductName() + " already exists in the cart");
        }

        if (product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + " is not available");
        }

        if (product.getQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        CartItem newCartItem = new CartItem();

        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity());

        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));
        cartRepository.save(cart);

        CartsDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        cartDTO.setProducts(Collections.singletonList(modelMapper.map(product, ProductDTO.class)));
        return cartDTO;
    }

    @Override
    public List<CartsDTO> getAllCarts() {
        List<Carts> carts = cartRepository.findAll();

        if (carts.size() == 0) {
            throw new APIException("No cart exists");
        }

        List<CartsDTO> cartDTOs = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

            List<ProductDTO> products = cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

            cartDTO.setProducts(products);

            return cartDTO;

        }).collect(Collectors.toList());

        return cartDTOs;
    }

    @Override
//        public CartDTO getCart(String emailId, Long cartId) {
    public CartsDTO getCart() {
        String loggedInEmail = authUtil.loggedInEmail();
        Long cartId = cartRepository.findCartByEmail(authUtil.loggedInEmail()).getCartId();
        Carts cart = cartRepository.findCartByEmailAndCartId(loggedInEmail, cartId);
        if (cart == null) {
            throw new ResourceNotFoundException(String.format("Cart not found with ID: %d", cartId));
        }
        CartsDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cart.getCartItems().forEach(c ->
                c.getProduct().setQuantity(c.getQuantity()));
        List<ProductDTO> products = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class))
                .toList();
        cartDTO.setProducts(products);
        return cartDTO;
    }

    @Transactional
    @Override
    public CartsDTO updateProductQuantityInCart(Long productId, Integer quantity) {

        String emailId = authUtil.loggedInEmail();
        Carts userCart = cartRepository.findCartByEmail(emailId);
        Long cartId = userCart.getCartId();

        Carts cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cart not found with ID: %d", cartId)));

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product not found with ID: %d", productId)));

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
            cartRepository.save(cart);
        }

        CartItem updatedItem = cartItemRepository.save(cartItem);
        if (updatedItem.getQuantity() == 0) {
            cartItemRepository.deleteById(updatedItem.getCartItemId());
        }


        CartsDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
            ProductDTO prd = modelMapper.map(item.getProduct(), ProductDTO.class);
            prd.setQuantity(item.getQuantity());
            return prd;
        });


        cartDTO.setProducts(productStream.toList());

        return cartDTO;
    }


    private Carts createCart() {
        Carts userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if (userCart != null) {
            return userCart;
        }

        Carts cart = new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());
        Carts newCart = cartRepository.save(cart);

        return newCart;
    }


    @Transactional
    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        Carts cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cart not found with ID: %d", cartId)));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new ResourceNotFoundException(String.format("Product not found with ID: %d", productId,cartId));
        }

        cart.setTotalPrice(cart.getTotalPrice() -
                (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItemRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

        return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
    }

    @Override
    public void updateProductInCarts(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " not available in the cart!!!");
        }

        double cartPrice = cart.getTotalPrice()
                - (cartItem.getProductPrice() * cartItem.getQuantity());

        cartItem.setProductPrice(product.getSpecialPrice());

        cart.setTotalPrice(cartPrice
                + (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItem = cartItemRepository.save(cartItem);
    }
}
