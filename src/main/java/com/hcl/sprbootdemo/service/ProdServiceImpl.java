package com.hcl.sprbootdemo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.sprbootdemo.entity.Products;
import com.hcl.sprbootdemo.exception.ResourceAlreadyExistsException;
import com.hcl.sprbootdemo.exception.ResourceNotFoundException;
import com.hcl.sprbootdemo.payload.ProductDTO;
import com.hcl.sprbootdemo.repository.ProductsRepository;

@Service
public class ProdServiceImpl implements ProductsService {

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ProductsRepository productsRepository;
	

	@Override
	public ProductDTO saveProduct(ProductDTO productDTO) {
		// TODO Auto-generated method stub

		if (productsRepository.existsByProductNameIgnoreCase(productDTO.getProductName())) {
			throw new ResourceAlreadyExistsException("Product", "productName", productDTO.getProductName());
		}
		Products product = modelMapper.map(productDTO, Products.class);
		if (product.getDiscount() != 0) {
			double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
			product.setSpecialPrice(specialPrice);
		} else {
			product.setSpecialPrice(product.getPrice());
		}
		Products savedProduct = productsRepository.save(product);
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public void deleteProduct(Long productId) {
		Products product = productsRepository.findById(productId)
				// .orElseThrow(() -> new RuntimeException("PRoduct NOT found with ID :" +
				// productId));
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		productsRepository.delete(product);
		System.out.println("Product " + productId + " deleted successfully !!!");
		// return modelMapper.map(product, ProductDTO.class);
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		List<Products> products = productsRepository.findAll();
		List<ProductDTO> productDTOS = products.stream().map(product -> {
			return modelMapper.map(product, ProductDTO.class);
		}

		).collect(Collectors.toList());
		return productDTOS;
	}

	@Override
	public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
		Optional<Products> productTobeUpdated = productsRepository.findById(productId);
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

		Products savedProduct = productsRepository.save(prod);
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductDTO findProductById(Long productId) {
		Products product = productsRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		return modelMapper.map(product, ProductDTO.class);

	}

	@Override
	public ProductDTO searchProductByKeyword(String keyword) {
		Products product = productsRepository.findByProductNameContainingIgnoreCase(keyword)
				// .orElseThrow(() -> new RuntimeException("No product found matching keyword: "
				// + keyword));
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productKeyword", keyword));
		return modelMapper.map(product, ProductDTO.class);
	}

}
