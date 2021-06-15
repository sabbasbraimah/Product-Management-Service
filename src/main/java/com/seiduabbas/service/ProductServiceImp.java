/**
 * 
 */
package com.seiduabbas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seiduabbas.domain.Product;
import com.seiduabbas.exception.ProductNotFoundException;
import com.seiduabbas.repository.ProductRepository;

/**
 * @author Owner
 *
 */
@Service
public class ProductServiceImp implements ProductService {

	private ProductRepository productRepo;
	
	@Autowired
	public ProductServiceImp(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	@Override
	public Product findProductById(Long id) {
		return productRepo.findById(id).orElse(null);
	}

	@Override
	public Product createProduct(Product product) {
		LocalDateTime dateTime = LocalDateTime.now();
		product.setCreatedDate(dateTime);
		return  productRepo.save(product);
	}

	@Override
	public List<Product> sortProductsByName() {
		 List<Product> products = 
					productRepo.findAll().stream()
					.sorted()
					.collect(Collectors.toList());
		 return products;
	}

	@Override
	public List<Product> filterProductsByCategory(Long categoryId) {
		List<Product> list = productRepo.findAll().stream().parallel().filter(e -> e.getId().equals(categoryId))
				.collect(Collectors.toList());
		return list;
	}

	@Override
	public void deleteById(Long productId) {
		Optional<Product> savedProduct = productRepo.findById(productId);
		if (savedProduct.isPresent()) {
			productRepo.deleteById(savedProduct.get().getId());
		} else {
			throw new ProductNotFoundException(productId);
		}
	}

	@Override
	public Product updateProduct(Product product, Long id) {
		
		//Product savedProduct = null;
		Optional<Product> optionalProduct = productRepo.findById(id);
		if (!optionalProduct.isPresent()) {
			throw new ProductNotFoundException(product.getId());
		}
		if(product.getName() != null)
			optionalProduct.get().setName(product.getName());
		if(product.getDescription() != null)
			optionalProduct.get().setDescription(product.getDescription());
		if(product.getCategoryId() != null)
			optionalProduct.get().setCategoryId(product.getCategoryId());
		if(product.getCreatedDate() != null)
			optionalProduct.get().setCreatedDate(product.getCreatedDate());
		if(product.getLastPurchaseDate() != null)
			optionalProduct.get().setLastPurchaseDate(product.getLastPurchaseDate());
		return productRepo.save(optionalProduct.get());


	}

}
