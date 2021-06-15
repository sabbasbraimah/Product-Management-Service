/**
 * 
 */
package com.seiduabbas.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Seidu Abbas
 *
 */
@ControllerAdvice
public class ProductServiceExceptionHandler {

	@ExceptionHandler(DuplicateProductException.class)
	public String duplicateProductHandler() {
	return "error/duplicate";
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public String productNotFoundHandler() {
	return "error/productNotFound";
	}
	
	
}
