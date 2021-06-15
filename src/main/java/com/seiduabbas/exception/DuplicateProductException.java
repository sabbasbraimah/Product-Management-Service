/**
 * 
 */
package com.seiduabbas.exception;

/**
 * @author Owner
 *
 */
public class DuplicateProductException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public DuplicateProductException(String message) {
		this.message = message;
	}
}
