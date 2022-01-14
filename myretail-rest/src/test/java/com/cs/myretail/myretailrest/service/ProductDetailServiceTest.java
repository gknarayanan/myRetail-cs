package com.cs.myretail.myretailrest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.cs.myretail.myretailrest.constant.MessageConstants;
import com.cs.myretail.myretailrest.dto.ProductDetailDTO;
import com.cs.myretail.myretailrest.exception.ProductNotFoundException;
import com.cs.myretail.myretailrest.model.ProductPrice;
import com.cs.myretail.myretailrest.service.impl.ProductDetailServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductDetailServiceTest {

	@Autowired
	@InjectMocks
	ProductDetailServiceImpl productDetailService;

	@Mock
	private ProductPriceService productPriceServiceMock;

	@Test
	public void retrieveProductDetail_success() {
		ProductPrice mockProductPrice = new ProductPrice();
		mockProductPrice.setId(12345L);
		mockProductPrice.setPrice((float) 19.99);
		mockProductPrice.setCurrency("USD");

		Mockito.when(productPriceServiceMock.getProductPriceById(Mockito.anyLong()))
				.thenReturn(Optional.of(mockProductPrice));

		ProductDetailDTO product = productDetailService.retrieveProductDetail(12345L);

		assertNotNull(product);
		assertEquals(product.getId(), 12345L);
		assertEquals(product.getName(), "Test");
		assertNotNull(product.getCurrentPrice());
		assertEquals(product.getCurrentPrice().getPrice(), (float) 19.99);
		assertEquals(product.getCurrentPrice().getCurrency(), "USD");
	}

	@Test
	public void retrieveProductDetail_noPriceFound() {
		Mockito.when(productPriceServiceMock.getProductPriceById(Mockito.anyLong()))
				.thenReturn(Optional.ofNullable(null));

		Exception exception = assertThrows(ProductNotFoundException.class, () -> {
			productDetailService.retrieveProductDetail(12345L);
		});

		String expectedMessage = MessageConstants.PRODUCT_PRICE_NOT_FOUND;
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.equals(expectedMessage));
	}

	@Test
	public void retrieveProductDetail_noPriceFound_nullId() {
		Mockito.when(productPriceServiceMock.getProductPriceById(null)).thenReturn(Optional.ofNullable(null));

		Exception exception = assertThrows(ProductNotFoundException.class, () -> {
			productDetailService.retrieveProductDetail(null);
		});

		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.equals(MessageConstants.PRODUCT_PRICE_NOT_FOUND));
	}

}
