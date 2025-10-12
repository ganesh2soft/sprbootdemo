package com.hcl.sprbootdemo.service;

import java.util.List;
import java.util.stream.Collectors;

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
import com.hcl.sprbootdemo.repository.CartItemRepository;
import com.hcl.sprbootdemo.repository.CartsRepository;
import com.hcl.sprbootdemo.repository.ProductsRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class ProductsServiceImpl implements ProductService {

	@Autowired
	ProductsRepository productRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public ProductDTO addProduct(Products products) {
		if(productRepository.existsByProductNameIgnoreCase(productDTO.getProductName())) {
			throw new ResourceAlreadyExistsException("Product", "productName", productDTO.getProductName());
		}
		Products product = modelMapper.map(productDTO, Products.class);
		if(product.getDiscount() !=0) {
		double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
		product.setSpecialPrice(specialPrice);
		}else {
			product.setSpecialPrice(product.getPrice());
		}
		Products savedProduct = productRepository.save(product);
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		List<Products> products = productRepository.findAll();
		List<ProductDTO> productDTOS = products.stream().map(product -> {
			return modelMapper.map(product, ProductDTO.class);
		}

		).collect(Collectors.toList());
		return productDTOS;
	}

	@Override
	public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
		Optional<Products> productTobeUpdated = productRepository.findById(productId);
		Products prod = null;
		if (productTobeUpdated.get() != null) {
			prod = productTobeUpdated.get();
			prod.setCategory(productDTO.getCategory());
			prod.setProductName(productDTO.getProductName());
			prod.setBrandName(productDTO.getBrandName());
			prod.setQuantity(productDTO.getQuantity());
			prod.setPrice(productDTO.getPrice());
			prod.setDiscount(productDTO.getDiscount());
			prod.setSpecialPrice(productDTO.getSpecialPrice());
		}

		Products savedProduct = productRepository.save(prod);
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductDTO deleteProduct(Long productId) {
		Products product = productRepository.findById(productId)
		//.orElseThrow(() -> new RuntimeException("PRoduct NOT found with ID :" + productId));
		.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		productRepository.delete(product);
		System.out.println("Product " + productId + " deleted successfully !!!");
		return modelMapper.map(product, ProductDTO.class);
	}

//	@Override
//	public Product searchProductByKeyword(String keyword) {
//
//		return null;
//	}
//
	@Override
	public Products getProductById(Long productId) {

		Products product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		return modelMapper.map(product, ProductDTO.class);

	}

	@Override
	public Products searchProductByKeyword(String keyword) {
		Products product = productRepository.findByProductNameContainingIgnoreCase(keyword)
				//.orElseThrow(() -> new RuntimeException("No product found matching keyword: " + keyword));
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productKeyword",keyword));
		return modelMapper.map(product, ProductDTO.class);
	}

}
