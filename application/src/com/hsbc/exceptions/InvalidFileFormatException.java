package com.hsbc.exceptions;

/*
 * DocumentNotFoundException
 */
@SuppressWarnings("serial")
public class InvalidFileFormatException extends Exception {
	
	public InvalidFileFormatException(String message) {
		super(message);
	}
	
	public InvalidFileFormatException(String message, Throwable cause) {
		super(message, cause);
	}
}
