package com.cs.myretail.myretailrest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.myretail.myretailrest.model.ProductPrice;
import com.cs.myretail.myretailrest.repository.ProductPriceRepository;

@Service
public class ProductPriceService {

	@Autowired
	private ProductPriceRepository productPriceRepository;

	public Optional<ProductPrice> getProductPriceById(Long productId) {
		Optional<ProductPrice> productPrice = productPriceRepository.findById(productId);
		
		return productPrice;
	}
	
	public ProductPriceRepository getProductPriceRepository() {
		return productPriceRepository;
	}

	public void setProductPriceRepository(ProductPriceRepository productPriceRepository) {
		this.productPriceRepository = productPriceRepository;
	}
}
