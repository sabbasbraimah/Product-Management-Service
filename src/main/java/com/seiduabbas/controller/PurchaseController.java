/**
 * 
 */
package com.seiduabbas.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.seiduabbas.service.ProductService;

/**
 * @author Seidu Abbas
 * @param <Product>
 *
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseController<Product> {
	
	private final ProductService productService;
	
	/**
	 * @param productService
	 */
	@Autowired
	public PurchaseController(ProductService productService) {
		super();
		this.productService = productService;
	}



	


}
