package com.cs.myretail.myretailrest.exception;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4845691758607356702L;

	public ProductNotFoundException(String message) {
		super(message);
	}

}
