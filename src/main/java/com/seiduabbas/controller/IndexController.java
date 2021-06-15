/**
 * 
 */
package com.seiduabbas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seiduabbas.service.ProductService;

/**
 * @author Owner
 *
 */
@Controller
public class IndexController {
	
	 private final ProductService productService;

	    @Autowired
	    public IndexController(ProductService productService) {
	        this.productService = productService;
	    }

	    @RequestMapping({"", "/", "/index"})
	    public String getIndexPage(Model model) {

	        model.addAttribute("products", productService.getAllProducts());

	        return "index";
	    }
}
