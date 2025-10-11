package com.hcl.sprbootdemo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.sprbootdemo.entity.Products;
import com.hcl.sprbootdemo.entity.Users;
import com.hcl.sprbootdemo.exception.ResourceAlreadyExistsException;
import com.hcl.sprbootdemo.exception.ResourceNotFoundException;


import com.hcl.sprbootdemo.repository.ProductsRepository;
import com.hcl.sprbootdemo.repository.UsersRepository;



@Service
@Transactional
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	ProductsRepository productRepository;

	

	@Override
	public Products saveProduct(Products product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}
	
	@Override
	public void deleteProduct(Long productId) {
		// TODO Auto-generated method stub
		productRepository.deleteById(productId);
	}

	@Override
	public List<Products> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	
	public Products updateProduct(Long productId, Products productDetails) {
        Products product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        product.setCategory(productDetails.getCategory());
        product.setProductName(productDetails.getProductName());
        product.setBrandName(productDetails.getBrandName());
        product.setQuantity(productDetails.getQuantity());
        product.setPrice(productDetails.getPrice());
        product.setDiscount(productDetails.getDiscount());
        product.setSpecialPrice(productDetails.getSpecialPrice());

        return productRepository.save(product);
    }

	@Override
	public Products findProductById(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
    }
	
	
	
/*
	@Override
	public ProductDTO addProduct(ProductDTO productDTO) {
		if(productRepository.existsByProductNameIgnoreCase(productDTO.getProductName())) {
			throw new ResourceAlreadyExistsException("Product", "productName", productDTO.getProductName());
		}
		Product product = modelMapper.map(productDTO, Product.class);
		Product savedProduct = productRepository.save(product);
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductDTO> ProductDTOS = products.stream().map(product -> {
			return modelMapper.map(product, ProductDTO.class);
		}

		).collect(Collectors.toList());
		return ProductDTOS;
	}

	@Override
	public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
		Optional<Product> productTobeUpdated = productRepository.findById(productId);
		Product prod = null;
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

		Product savedProduct = productRepository.save(prod);
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductDTO deleteProduct(Long productId) {
		Product product = productRepository.findById(productId)
				// .orElseThrow(() -> new RuntimeException("PRoduct NOT found with ID :" +
				// productId));
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
	public ProductDTO getProductById(Long productId) {

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		return modelMapper.map(product, ProductDTO.class);

	}
*/
}
