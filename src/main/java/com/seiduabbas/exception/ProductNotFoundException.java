/**
 * 
 */
package com.seiduabbas.exception;

/**
 * @author Owner
 *
 */
public class ProductNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private Long id;
	
	public ProductNotFoundException(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
