package com.hcl.sprbootdemo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.hcl.sprbootdemo.payload.ProductDTO;
import com.hcl.sprbootdemo.service.ProductsService;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

	@Autowired
	ProductsService productsService;

	@GetMapping("/hello")
	public String helloFn() {
		return "Product Controller response!";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/addproducts")
	public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
		System.out.println("Control came to add prdt");
		ProductDTO savedProduct = productsService.saveProduct(productDTO);
		return new ResponseEntity<ProductDTO>(savedProduct, HttpStatus.CREATED);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/get/{id}")
	public ProductDTO getProduct(@PathVariable Long id) {
		return productsService.findProductById(id);
	}

	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/getall")
	public List<ProductDTO> getAllProducts() {
		return productsService.getAllProducts();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/deleteproducts/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productsService.deleteProduct(id);
		return "Product deleted successfully!";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/updateproducts/{id}")
	public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO product) {
		return productsService.updateProduct(id, product);
	}

	
}
