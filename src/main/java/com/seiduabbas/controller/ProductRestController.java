/**
 * 
 */
package com.seiduabbas.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.seiduabbas.domain.Product;
import com.seiduabbas.domain.Products;
import com.seiduabbas.exception.Error;
import com.seiduabbas.exception.ProductNotFoundException;
import com.seiduabbas.service.ProductService;

/**
 * @author Seidu Abbas
 *
 */
@RestController
@RequestMapping("/rest")
public class ProductRestController {
	
	private  final ProductService productService;

	@Autowired
	public ProductRestController(ProductService productService) {
		this.productService = productService;
	}
	
	/**
	 * Handles product not found exceptions
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody Error productNotFound(ProductNotFoundException e) {
		long productId = e.getId();
		return  new Error(4, "Product [" + productId + "] not found");
	}
		
	/**
	 * Rest version of getAllList
	 * @return
	 */
	@RequestMapping(value = "restProducts", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)  
	public List<Product> restGetList() {
		return productService.getAllProducts();
	}
	
	/**
	 * Rest sortByName
	 * @return
	 */
	@RequestMapping(value = "restSortByName", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)  
	public List<Product> restApiSortProductsByName() {
		List<Product> products = productService.sortProductsByName();				
		return products;
	}

	/**
	 * Rest finds a product given its correct id 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/{restProductId}",consumes="application/json", produces="application/json")
	@ResponseStatus(HttpStatus.OK)  
	public Product  restFindProductById(@PathVariable Long restProductId) {
		Optional<Product> optionalProduct = Optional.ofNullable(productService
				.findProductById(restProductId));
		if (!optionalProduct.isPresent()) 
			throw new ProductNotFoundException(restProductId);
		return optionalProduct.get();

	}
	
	/**
	 * Rest Updates a product given its correct id 
	 * @param id
	 * @return
	 */
	@PutMapping(path="/{restProductId}",consumes ="application/json", produces = "application/json")
			@ResponseStatus(HttpStatus.OK)  
	public Product restUpdate(@RequestBody Product product,
			@PathVariable Long restProductId, BindingResult result) {
		if (result.hasErrors()) {
            throw  new ProductNotFoundException(restProductId);
        } else {
        	product.setId(restProductId);
        	Product savedProduct = productService.createProduct(product);
   		 	return savedProduct; 
        }		
	}
		
	@PostMapping( consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)  
	public Product restSaveProduct(@Validated @RequestBody Product product) {
		Product savedProduct = productService.createProduct(product);		
		 return savedProduct; 
	}

	@DeleteMapping("restDelete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)  
	public void restDeleteProduct(@PathVariable Long id) {	
		productService.deleteById(id) ;	
    }	
 }


