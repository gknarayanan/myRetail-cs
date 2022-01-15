package com.cs.myretail.myretailrest.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cs.myretail.myretailrest.constant.MessageConstants;
import com.cs.myretail.myretailrest.exception.ProductNotFoundException;
import com.cs.myretail.myretailrest.service.ProductInformationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductInformationServiceImpl implements ProductInformationService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${product-information.url.endpoint}")
	private String productInformationBaseEndpoint;

	@Value("${product-information.url.key}")
	private String productInformationKey;

	@Override
	public String getProductNameById(Long productId) throws ProductNotFoundException {
		ResponseEntity<String> response = getProductInformationById(productId);
		String name = parseResponseForProductName(response.getBody());

		if (name == null) {
			throw new ProductNotFoundException(MessageConstants.PRODUCT_INFORMATION_NOT_FOUND);
		}

		return name;
	}

	private ResponseEntity<String> getProductInformationById(Long productId) throws ProductNotFoundException {
		ResponseEntity<String> response;
		try {
			Map<String, String> vars = new HashMap<>();
			vars.put("key", productInformationKey);
			vars.put("tcin", String.valueOf(productId));

			response = restTemplate.getForEntity(productInformationBaseEndpoint, String.class, vars);
		} catch (RestClientException e) {
			throw new ProductNotFoundException(MessageConstants.PRODUCT_INFORMATION_NOT_FOUND);
		}

		if (!response.getStatusCode().equals(HttpStatus.OK)) {
			throw new ProductNotFoundException(MessageConstants.PRODUCT_INFORMATION_NOT_FOUND);
		}

		return response;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String parseResponseForProductName(String response) throws ProductNotFoundException {
		String name = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Map> productInfo = mapper.readValue(response, Map.class);
			Map<String, Map> data = productInfo.get("data");
			Map<String, Map> product = data.get("product");
			Map<String, Map> item = product.get("item");
			Map<String, String> productDescription = item.get("product_description");

			name = productDescription.get("title");
		} catch (Exception ex) {
			throw new ProductNotFoundException(MessageConstants.PRODUCT_INFORMATION_NOT_FOUND);
		}

		return name;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getProductInformationBaseEndpoint() {
		return productInformationBaseEndpoint;
	}

	public void setProductInformationBaseEndpoint(String productInformationBaseEndpoint) {
		this.productInformationBaseEndpoint = productInformationBaseEndpoint;
	}

	public String getProductInformationKey() {
		return productInformationKey;
	}

	public void setProductInformationKey(String productInformationKey) {
		this.productInformationKey = productInformationKey;
	}

}
