package com.cs.myretail.myretailrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductPriceDTO {

	@JsonProperty("value")
	private Float price;

	@JsonProperty("currency_code")
	private String currency;

	public ProductPriceDTO() {
		super();
	}

	public ProductPriceDTO(Float price, String currency) {
		super();
		this.price = price;
		this.currency = currency;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
