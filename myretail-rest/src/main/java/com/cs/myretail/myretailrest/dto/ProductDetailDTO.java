package com.cs.myretail.myretailrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDetailDTO {

	private Long id;

	private String name;

	@JsonProperty("current_price")
	private ProductPriceDTO currentPrice;

	public ProductDetailDTO() {
		super();
	}

	public ProductDetailDTO(Long id, String name, ProductPriceDTO currentPrice) {
		super();
		this.id = id;
		this.name = name;
		this.currentPrice = currentPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductPriceDTO getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(ProductPriceDTO currentPrice) {
		this.currentPrice = currentPrice;
	}

}
