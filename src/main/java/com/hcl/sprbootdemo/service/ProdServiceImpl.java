package com.hcl.sprbootdemo.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.sprbootdemo.entity.Products;
import com.hcl.sprbootdemo.exception.ResourceNotFoundException;
import com.hcl.sprbootdemo.payload.ProductDTO;
import com.hcl.sprbootdemo.repository.ProductsRepository;

@Service
public class ProdServiceImpl implements ProductsService {
	private static final Logger logger = LoggerFactory.getLogger(ProdServiceImpl.class);

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ProductsRepository productsRepository;

	@Override
	public ProductDTO saveProduct(ProductDTO productDTO) {

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
	public ProductDTO findProductById(Long productId) {
		Products product = productsRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		return modelMapper.map(product, ProductDTO.class);

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
	public void deleteProduct(Long productId) {
		Products product = productsRepository.findById(productId)

				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		productsRepository.delete(product);
		logger.info("Product " + productId + " deleted successfully !!!");

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
}
