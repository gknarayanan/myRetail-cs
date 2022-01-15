package com.cs.myretail.myretailrest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.cs.myretail.myretailrest.constant.MessageConstants;
import com.cs.myretail.myretailrest.exception.ProductNotFoundException;
import com.cs.myretail.myretailrest.service.impl.ProductInformationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductInformationServiceTest {

	@Autowired
	@InjectMocks
	ProductInformationServiceImpl productInformationServiceImpl;

	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(productInformationServiceImpl, "productInformationBaseEndpoint",
				"https://test.com/case_study_v1?key={key}&tcin={tcin}");
		ReflectionTestUtils.setField(productInformationServiceImpl, "productInformationKey", "12345");
	}

	@Mock
	private RestTemplate restTemplateMock;

	@Test
	public void getProductNameById_success() {
		String mockResponse = "{\"data\":{\"product\":{\"tcin\":\"13860428\",\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\",\"downstream_description\":\"Jeff \\\"The Dude\\\" Lebowski (Bridges) is the victim of mistaken identity. Thugs break into his apartment in the errant belief that they are accosting Jeff Lebowski, the eccentric millionaire philanthropist, not the laid-back, unemployed Jeff Lebowski. In the aftermath, \\\"The Dude\\\" seeks restitution from his wealthy namesake. He and his buddies (Goodman and Buscemi) are swept up in a kidnapping plot that quickly spins out of control.\"},\"enrichment\":{\"images\":{\"primary_image_url\":\"https://target.scene7.com/is/image/Target/GUEST_bac49778-a5c7-4914-8fbe-96e9cd549450\"}},\"product_classification\":{\"product_type_name\":\"ELECTRONICS\",\"merchandise_type_name\":\"Movies\"},\"primary_brand\":{\"name\":\"Universal Home Video\"}}}}}";

		Mockito.when(restTemplateMock.getForEntity(Mockito.anyString(), Mockito.any(), Mockito.anyMap()))
				.thenReturn(new ResponseEntity<Object>(mockResponse, HttpStatus.OK));

		String name = productInformationServiceImpl.getProductNameById(13860428L);
		assertEquals(name, "The Big Lebowski (Blu-ray)");
	}

	@Test
	public void getProductNameById_noProductFound() {
		String mockResponse = "{\"errors\":[{\"message\":\"No product found with tcin 123\"}]}";

		Mockito.when(restTemplateMock.getForEntity(Mockito.anyString(), Mockito.any(), Mockito.anyMap()))
				.thenReturn(new ResponseEntity<Object>(mockResponse, HttpStatus.OK));

		Exception exception = assertThrows(ProductNotFoundException.class, () -> {
			productInformationServiceImpl.getProductNameById(123L);
		});

		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.equals(MessageConstants.PRODUCT_INFORMATION_NOT_FOUND));
	}
	
	@Test
	public void getProductNameById_apiError() {
		Mockito.when(restTemplateMock.getForEntity(Mockito.anyString(), Mockito.any(), Mockito.anyMap()))
				.thenReturn(new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR));

		Exception exception = assertThrows(ProductNotFoundException.class, () -> {
			productInformationServiceImpl.getProductNameById(123L);
		});

		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.equals(MessageConstants.PRODUCT_INFORMATION_NOT_FOUND));
	}
}
