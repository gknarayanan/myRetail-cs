package com.cs.myretail.myretailrest.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.myretail.myretailrest.model.ProductPrice;
import com.cs.myretail.myretailrest.repository.ProductPriceRepository;
import com.cs.myretail.myretailrest.service.ProductPriceService;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {

	@Autowired
	private ProductPriceRepository productPriceRepository;

	@Override
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
