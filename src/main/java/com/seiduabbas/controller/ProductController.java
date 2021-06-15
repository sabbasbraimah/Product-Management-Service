/**
 * 
 */
package com.seiduabbas.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.seiduabbas.domain.Product;
import com.seiduabbas.domain.Products;
import com.seiduabbas.exception.ProductNotFoundException;
import com.seiduabbas.exception.Error;
import org.springframework.util.StringUtils;

import com.seiduabbas.service.ProductService;

/**
 * @author Seidu Abbas
 *
 */
@Controller
@RequestMapping("/products")
public class ProductController {

	private static final String PRODUCT_CREATE_OR_UPDATE_FORM = "products/productForm";
	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
	
	@GetMapping("/productForm")
	public String getNewProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return PRODUCT_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/new")
	@ResponseStatus(HttpStatus.CREATED)
	public String saveProduct(@Validated Product product, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
            return PRODUCT_CREATE_OR_UPDATE_FORM;
        } else {
            Product prod = this.productService.createProduct(product);
            model.addAttribute("product", prod);
            return "redirect:/products/productShow";
        }
	}
	
	@RequestMapping( method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products/productsList";
	}

	@RequestMapping(value = "/sortByName", method = RequestMethod.GET)
	public String sortProductsByName(Model model) {
		List<Product> products = productService.sortProductsByName();
		model.addAttribute("products", products);
		return "products/productsList";
	}

	@RequestMapping("/{id}")
	public String findProduct(@PathVariable Long id, Model model) {	
		Product product = productService.findProductById(id);
		model.addAttribute("product", product);
        return 	"products/productShow";
	}

	@RequestMapping(value = "/{productId}/update", method = RequestMethod.GET)
	public String getUpdateProductForm(@PathVariable("productId") String productId, ModelMap model) {
		Optional<Product> optionalProduct = Optional
				.ofNullable(productService.findProductById(Long.valueOf(productId)));
		if (!optionalProduct.isPresent()) {
			throw new ProductNotFoundException(Long.valueOf(productId));
		} else {
			model.addAttribute(optionalProduct.get());
		}
		return  PRODUCT_CREATE_OR_UPDATE_FORM ;
	}

	@RequestMapping(value = "/{productId}/update", method = RequestMethod.POST)
	public String processUpdateProductForm(@Validated Product product,  
			@PathVariable("productId") String productId,  BindingResult result) {
		
		 if (result.hasErrors()) {
	            return PRODUCT_CREATE_OR_UPDATE_FORM;
	        } else {
	        	product.setId(Long.valueOf(productId));
	            this.productService.createProduct(product);
	            return "redirect:/products/"+ Long.valueOf(productId) ;
	        }
		
		}

	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable String id) {
		productService.deleteById(Long.valueOf(id));
		return "redirect:/";
	}
	
	@GetMapping("/purchase/{id}")
	public String purchaseDate(@PathVariable("id")String id ) {
		Product prod =  productService.findProductById(Long.valueOf(id));
		prod.setLastPurchaseDate(LocalDateTime.now());
		this.productService.createProduct(prod);
		return "redirect:/";
	}

	@RequestMapping(value = { "/products.json", "/product.xml" })
	public @ResponseBody Products showResourcesProductList() {
		// Here we are returning an object of type 'Products' rather than a collection
		// of Product objects
		// so it is simpler for JSon/Object mapping
		Products products = new Products();
		products.getProductList().addAll(productService.getAllProducts());
		return products;
	}

	/**
	 * Handles product not found exceptions
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody Error productNotFound(ProductNotFoundException e) {
		long productId = e.getId();
		return new Error(4, "Product [" + productId + "] not found");
	}

}
