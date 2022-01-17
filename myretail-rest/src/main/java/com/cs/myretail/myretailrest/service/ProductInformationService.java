package com.cs.myretail.myretailrest.service;

import com.cs.myretail.myretailrest.exception.ProductNotFoundException;

public interface ProductInformationService {

	String getProductNameById(Long productId) throws ProductNotFoundException;

}
