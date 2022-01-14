package com.cs.myretail.myretailrest.service;

import java.util.Optional;

import com.cs.myretail.myretailrest.model.ProductPrice;

public interface ProductPriceService {

	Optional<ProductPrice> getProductPriceById(Long productId);

}
