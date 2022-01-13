package com.cs.myretail.myretailrest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.myretail.myretailrest.dto.ProductDetailDTO;
import com.cs.myretail.myretailrest.dto.ProductPriceDTO;
import com.cs.myretail.myretailrest.exception.ProductNotFoundException;
import com.cs.myretail.myretailrest.model.ProductPrice;

@Service
public class ProductDetailService {

	@Autowired
	private ProductPriceService productPriceService;

	public ProductDetailDTO retrieveProductDetail(Long id) throws ProductNotFoundException {
		String productName = "Test"; // TODO - Get name from API here

		Optional<ProductPrice> productPrice = productPriceService.getProductPriceById(id);
		if (productPrice.isEmpty()) {
			throw new ProductNotFoundException("Product price is not available.");
		}

		ProductDetailDTO product = constructProductDetailDTO(id, productName, productPrice.get());

		return product;
	}

	private ProductDetailDTO constructProductDetailDTO(Long id, String productName, ProductPrice productPrice) {
		ProductPriceDTO price = new ProductPriceDTO(productPrice.getPrice(), productPrice.getCurrency());
		ProductDetailDTO product = new ProductDetailDTO(id, productName, price);

		return product;
	}

	public ProductPriceService getProductPriceService() {
		return productPriceService;
	}

	public void setProductPriceService(ProductPriceService productPriceService) {
		this.productPriceService = productPriceService;
	}

}
