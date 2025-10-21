package com.hcl.sprbootdemo.service;

import java.util.List;

import com.hcl.sprbootdemo.payload.ProductDTO;

public interface ProductsService {
	public ProductDTO saveProduct(ProductDTO product);

	public ProductDTO findProductById(Long productId);

	public List<ProductDTO> getAllProducts();

	public void deleteProduct(Long productId);

	public ProductDTO updateProduct(Long productId, ProductDTO product);

}
