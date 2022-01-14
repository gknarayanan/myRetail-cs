package com.cs.myretail.myretailrest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.cs.myretail.myretailrest.model.ProductPrice;
import com.cs.myretail.myretailrest.repository.ProductPriceRepository;
import com.cs.myretail.myretailrest.service.impl.ProductPriceServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductPriceServiceTest {

	@InjectMocks
	@Autowired
	ProductPriceServiceImpl productPriceService;

	@Mock
	private ProductPriceRepository productPriceRepositoryMock;

	@Test
	public void getProductPriceById_success() throws Exception {
		ProductPrice mockProductPrice = new ProductPrice();
		mockProductPrice.setId(12345L);
		mockProductPrice.setPrice((float) 19.99);
		mockProductPrice.setCurrency("USD");

		Mockito.when(productPriceRepositoryMock.findById(12345L)).thenReturn(Optional.ofNullable(mockProductPrice));

		Optional<ProductPrice> price = productPriceService.getProductPriceById(12345L);
		assertEquals(price.isPresent(), true);
		assertEquals(price.get().getId(), 12345L);
		assertEquals(price.get().getPrice(), (float) 19.99);
		assertEquals(price.get().getCurrency(), "USD");
	}
	
	@Test
	public void getProductPriceById_noEntry() throws Exception {
		Mockito.when(productPriceRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		Optional<ProductPrice> price = productPriceService.getProductPriceById(12345L);
		assertEquals(price.isPresent(), false);
	}
	
}
