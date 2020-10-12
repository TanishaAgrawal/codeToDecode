package com.hsbc.exceptions;

/*
 * DocumentNotFoundException
 */
@SuppressWarnings("serial")
public class InputStreamEmptyException extends Exception {
	
	public InputStreamEmptyException(String message) {
		super(message);
	}
	
	public InputStreamEmptyException(String message, Throwable cause) {
		super(message, cause);
	}
}
