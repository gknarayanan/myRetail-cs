package com.cs.myretail.myretailrest.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.myretail.myretailrest.constant.MessageConstants;
import com.cs.myretail.myretailrest.dto.ProductDetailDTO;
import com.cs.myretail.myretailrest.dto.ProductPriceDTO;
import com.cs.myretail.myretailrest.exception.ProductNotFoundException;
import com.cs.myretail.myretailrest.model.ProductPrice;
import com.cs.myretail.myretailrest.service.ProductDetailService;
import com.cs.myretail.myretailrest.service.ProductInformationService;
import com.cs.myretail.myretailrest.service.ProductPriceService;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

	@Autowired
	private ProductPriceService productPriceService;

	@Autowired
	private ProductInformationService productInformationService;

	@Override
	public ProductDetailDTO retrieveProductDetail(Long id) throws ProductNotFoundException {
		String productName = productInformationService.getProductNameById(id);

		Optional<ProductPrice> productPrice = productPriceService.getProductPriceById(id);
		if (productPrice.isEmpty()) {
			throw new ProductNotFoundException(MessageConstants.PRODUCT_PRICE_NOT_FOUND);
		}

		ProductDetailDTO product = constructProductDetailDTO(id, productName, productPrice.get());

		return product;
	}

	@Override
	public ProductDetailDTO updateProductPrice(Long id, ProductPriceDTO newPrice) throws ProductNotFoundException {
		String productName = productInformationService.getProductNameById(id);
		Optional<ProductPrice> productPrice = productPriceService.getProductPriceById(id);
		ProductPrice currentPrice = null;

		if (productPrice.isEmpty()) {
			currentPrice = new ProductPrice();
			currentPrice.setId(id);
		} else {
			currentPrice = productPrice.get();
		}

		currentPrice.setPrice(newPrice.getPrice());
		currentPrice.setCurrency(newPrice.getCurrency());
		currentPrice = productPriceService.updateProductPrice(currentPrice);
		ProductDetailDTO product = constructProductDetailDTO(id, productName, currentPrice);
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

	public ProductInformationService getProductInformationService() {
		return productInformationService;
	}

	public void setProductInformationService(ProductInformationService productInformationService) {
		this.productInformationService = productInformationService;
	}

}
