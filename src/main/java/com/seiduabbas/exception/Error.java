/**
 * 
 */
package com.seiduabbas.exception;

import org.springframework.http.ResponseEntity;

/**
 * @author Owner
 *
 */
public class Error {
	
	private int code;
	private String message;
	
	public Error(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}

	public ResponseEntity<Error> thenReturn() {
		// TODO Auto-generated method stub
		return new ResponseEntity<Error>(this, null);
	}

}
