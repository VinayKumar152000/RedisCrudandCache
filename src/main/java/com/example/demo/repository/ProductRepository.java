package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.example.demo.domain.Product;

@Repository
public class ProductRepository {

	private static final String HASH_KEY = "Product";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public Product save(Product product) {
		redisTemplate.opsForHash().put(HASH_KEY, product.getId(), product);
		return product;
	}

	public List<Object> findAll() {
		return redisTemplate.opsForHash().values(HASH_KEY);
	}

	public Product findById(int id) {
		System.out.println("called findById from redis db ");
		return (Product) redisTemplate.opsForHash().get(HASH_KEY, id);
	}

	public Product update(Product product, int id) {
		redisTemplate.opsForHash().put(HASH_KEY, id, product);
		return findById(id);

	}

	public String delete(int id) {
		redisTemplate.opsForHash().delete(HASH_KEY, id);
		return "Product is Deleted";
	}
}
