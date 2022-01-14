package com.cs.myretail.myretailrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.myretail.myretailrest.dto.ProductDetailDTO;
import com.cs.myretail.myretailrest.service.ProductDetailService;

@RestController
@RequestMapping(value = "/product")
public class ProductDetailController {

	@Autowired
	private ProductDetailService productDetailService;

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ProductDetailDTO> getProductDetailById(@PathVariable Long id) throws Exception {
		ProductDetailDTO productDetail = productDetailService.retrieveProductDetail(id);

		ResponseEntity<ProductDetailDTO> response = new ResponseEntity<ProductDetailDTO>(productDetail, HttpStatus.OK);

		return response;
	}

	public ProductDetailService getProductDetailService() {
		return productDetailService;
	}

	public void setProductDetailService(ProductDetailService productDetailService) {
		this.productDetailService = productDetailService;
	}
}