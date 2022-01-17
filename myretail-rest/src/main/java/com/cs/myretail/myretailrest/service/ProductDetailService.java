package com.cs.myretail.myretailrest.service;

import com.cs.myretail.myretailrest.dto.ProductDetailDTO;
import com.cs.myretail.myretailrest.dto.ProductPriceDTO;
import com.cs.myretail.myretailrest.exception.ProductNotFoundException;

public interface ProductDetailService {

	ProductDetailDTO retrieveProductDetail(Long id) throws ProductNotFoundException;

	ProductDetailDTO updateProductPrice(Long id, ProductPriceDTO newPrice) throws ProductNotFoundException;

}
