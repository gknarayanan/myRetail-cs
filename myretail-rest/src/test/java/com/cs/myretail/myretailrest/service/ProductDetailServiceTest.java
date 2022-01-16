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
import com.cs.myretail.myretailrest.dto.ProductPriceDTO;
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

	@Mock
	private ProductInformationService productInformationServiceMock;

	@Test
	public void retrieveProductDetail_success() {
		ProductPrice mockProductPrice = new ProductPrice();
		mockProductPrice.setId(12345L);
		mockProductPrice.setPrice(19.99f);
		mockProductPrice.setCurrency("USD");

		Mockito.when(productInformationServiceMock.getProductNameById(Mockito.anyLong())).thenReturn("Product Name");

		Mockito.when(productPriceServiceMock.getProductPriceById(Mockito.anyLong()))
				.thenReturn(Optional.of(mockProductPrice));

		ProductDetailDTO product = productDetailService.retrieveProductDetail(12345L);

		assertNotNull(product);
		assertEquals(product.getId(), 12345L);
		assertEquals(product.getName(), "Product Name");
		assertNotNull(product.getCurrentPrice());
		assertEquals(product.getCurrentPrice().getPrice(), 19.99f);
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
	public void retrieveProductDetail_noProductInformationFound() {
		Mockito.when(productInformationServiceMock.getProductNameById(Mockito.anyLong()))
				.thenThrow(new ProductNotFoundException(MessageConstants.PRODUCT_PRICE_NOT_FOUND));

		Exception exception = assertThrows(ProductNotFoundException.class, () -> {
			productDetailService.retrieveProductDetail(111L);
		});

		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.equals(MessageConstants.PRODUCT_PRICE_NOT_FOUND));
	}

	@Test
	public void updateProductPrice_successUpdatePrice() {
		Long id = 12345L;
		String productName = "Product Name";

		ProductPrice currProductPrice = new ProductPrice();
		currProductPrice.setId(id);
		currProductPrice.setPrice(19.99f);
		currProductPrice.setCurrency("USD");

		ProductPriceDTO newPrice = new ProductPriceDTO(15.99f, "CAD");
		ProductPrice newProductPrice = new ProductPrice();
		newProductPrice.setId(id);
		newProductPrice.setPrice(15.99f);
		newProductPrice.setCurrency("CAD");

		Mockito.when(productInformationServiceMock.getProductNameById(id)).thenReturn(productName);

		Mockito.when(productPriceServiceMock.getProductPriceById(Mockito.anyLong()))
				.thenReturn(Optional.of(currProductPrice));

		Mockito.when(productPriceServiceMock.updateProductPrice(Mockito.any(ProductPrice.class)))
				.thenReturn(newProductPrice);

		ProductDetailDTO product = productDetailService.updateProductPrice(id, newPrice);

		assertNotNull(product);
		assertEquals(product.getId(), id);
		assertEquals(product.getName(), productName);
		assertNotNull(product.getCurrentPrice());
		assertEquals(product.getCurrentPrice().getPrice(), 15.99f);
		assertEquals(product.getCurrentPrice().getCurrency(), "CAD");
	}

	@Test
	public void updateProductPrice_successNewPrice() {
		Long id = 12345L;
		String productName = "Product Name";

		ProductPriceDTO newPrice = new ProductPriceDTO(15.99f, "CAD");
		ProductPrice newProductPrice = new ProductPrice();
		newProductPrice.setId(id);
		newProductPrice.setPrice(15.99f);
		newProductPrice.setCurrency("CAD");

		Mockito.when(productInformationServiceMock.getProductNameById(id)).thenReturn(productName);

		Mockito.when(productPriceServiceMock.getProductPriceById(id)).thenReturn(Optional.ofNullable(null));

		Mockito.when(productPriceServiceMock.updateProductPrice(Mockito.any(ProductPrice.class)))
				.thenReturn(newProductPrice);

		ProductDetailDTO product = productDetailService.updateProductPrice(id, newPrice);

		assertNotNull(product);
		assertEquals(product.getId(), id);
		assertEquals(product.getName(), productName);
		assertNotNull(product.getCurrentPrice());
		assertEquals(product.getCurrentPrice().getPrice(), 15.99f);
		assertEquals(product.getCurrentPrice().getCurrency(), "CAD");
	}

	@Test
	public void updateProductPrice_noProductFound() {
		Long id = 12345L;

		ProductPriceDTO newPrice = new ProductPriceDTO(15.99f, "CAD");

		Mockito.when(productInformationServiceMock.getProductNameById(id))
				.thenThrow(new ProductNotFoundException(MessageConstants.PRODUCT_INFORMATION_NOT_FOUND));

		Exception exception = assertThrows(ProductNotFoundException.class, () -> {
			productDetailService.updateProductPrice(id, newPrice);
		});

		String expectedMessage = MessageConstants.PRODUCT_INFORMATION_NOT_FOUND;
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.equals(expectedMessage));
	}
}
