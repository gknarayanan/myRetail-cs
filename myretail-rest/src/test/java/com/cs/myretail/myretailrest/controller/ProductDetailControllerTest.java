package com.cs.myretail.myretailrest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cs.myretail.myretailrest.constant.MessageConstants;
import com.cs.myretail.myretailrest.dto.ProductDetailDTO;
import com.cs.myretail.myretailrest.dto.ProductPriceDTO;
import com.cs.myretail.myretailrest.exception.CustomExceptionResponse;
import com.cs.myretail.myretailrest.exception.ProductNotFoundException;
import com.cs.myretail.myretailrest.service.impl.ProductDetailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ProductDetailController.class)
@ExtendWith(MockitoExtension.class)
public class ProductDetailControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductDetailServiceImpl productDetailServiceMock;

	@Test
	public void getProductDetailById_success() throws Exception {
		ProductPriceDTO mockProductPriceDTO = new ProductPriceDTO((float) 19.99, "USD");
		Mockito.when(productDetailServiceMock.retrieveProductDetail(Mockito.anyLong()))
				.thenReturn(new ProductDetailDTO(12345L, "Test", mockProductPriceDTO));

		final String product_url = "/product/12345";
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get(product_url)
				.accept(MediaType.APPLICATION_JSON_VALUE);

		final MvcResult actual = mockMvc.perform(requestBuilder).andReturn();

		assertNotNull(actual.getResponse());

		final String expected = "{\"id\":12345,\"name\":\"Test\",\"current_price\":{\"value\": 19.99,\"currency_code\":\"USD\"}}";

		JSONAssert.assertEquals(expected, actual.getResponse().getContentAsString(), false);
	}

	@Test
	public void getProductDetailById_noPriceFound() throws Exception {
		Mockito.when(productDetailServiceMock.retrieveProductDetail(Mockito.anyLong()))
				.thenThrow(new ProductNotFoundException(MessageConstants.PRODUCT_PRICE_NOT_FOUND));

		final String product_url = "/product/12345";
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get(product_url)
				.accept(MediaType.APPLICATION_JSON_VALUE);

		final MvcResult actual = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = actual.getResponse();
		assertNotNull(response);

		CustomExceptionResponse customResponse = new ObjectMapper().readValue(response.getContentAsString(),
				CustomExceptionResponse.class);
		assertNotNull(customResponse);
		assertNotNull(customResponse.getTimestamp());
		assertEquals(customResponse.getMessage(), MessageConstants.PRODUCT_PRICE_NOT_FOUND);

	}

}
