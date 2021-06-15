/**
 * 
 */
package com.seiduabbas.service;

import java.util.List;

import com.seiduabbas.domain.Product;

/**
 * @author Owner
 *
 */
public interface ProductService {

	List<Product> getAllProducts();
	
	Product findProductById(Long productId);

	List<Product> sortProductsByName();

	List<Product> filterProductsByCategory(Long categoryId);

	Product createProduct(Product product);
	
	void deleteById(Long productI) ;
	
	Product updateProduct(Product product, Long id) ;

}
