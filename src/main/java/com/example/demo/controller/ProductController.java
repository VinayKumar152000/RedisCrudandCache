package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.example.demo.domain.Product;
import com.example.demo.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping
	public List<Object> getAllProducts() {
		return productRepository.findAll();
	}

	// for having data from cache,first time query will go to db,after that
												// we will getdata from cache -> using unless we mentioned when to add 
	//data to cache and retrieve it
	@GetMapping("/{id}")
	@Cacheable(key = "#id",value = "Product",unless = "#result.price < 1000")
	public Product getProductById(@PathVariable("id") int id) {
		return productRepository.findById(id);
	}

	@PostMapping
	public Product createProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}

	@PutMapping("/{id}")
	public Product updateProduct(@RequestBody Product product, @PathVariable("id") int id) {
		return productRepository.update(product, id);
	}

	@DeleteMapping("/{id}")
	 @CacheEvict(key = "#id",value = "Product")//-> delete data from db and the changes must also presist in cache we use
	//cache evict
	public String deleteProduct(@PathVariable("id") int id) {
		return productRepository.delete(id);
	}

}
