package com.seiduabbas.servicetest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.seiduabbas.domain.Product;
import com.seiduabbas.exception.ProductNotFoundException;
import com.seiduabbas.repository.ProductRepository;
import com.seiduabbas.service.ProductService;
import com.seiduabbas.service.ProductServiceImp;

import static org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImpTest {

	@InjectMocks
	private ProductService productService = mock(ProductServiceImp.class);

	@Mock
	private ProductRepository productRepo;

	private Product product;
	private Product product2;
	private Product product3;
	private List<Product> productList;

	@BeforeEach
	void setUp() {
		productList = new ArrayList<>();
		product = new Product();
		product2 = new Product();
		product3 = new Product();		
		
		product.setId(1L);
		product2.setId(2L);
		product3.setId(3L);
		productList.add(product);
		productList.add(product2);
		productList.add(product3);
	}

	@Test
	void getAllProducts() {
		// given		
		given(productService.getAllProducts()).willReturn(productList);
		// when
		List<Product> returnedProductList = productService.getAllProducts();
		// then
		assertThat(returnedProductList).isNotNull();
		assertThat(returnedProductList.size()).isNotEqualTo(2);
		assertThat(returnedProductList.size()).isEqualTo(3);
		
		verify(productService, times(1)).getAllProducts();
		verify(productService, atLeastOnce()).getAllProducts();
	}

	@Test
	void findProductById() {

		// given
		product.setId(1L);
		//Product product = new Product();
		given(productService.findProductById(1L)).willReturn(product);

		// when
		Product returnedProduct = productService.findProductById(1L);

		// then
		assertThat(returnedProduct).isNotNull();
		assertThat(returnedProduct.getId()).isNotEqualTo(2L);
		assertThat(returnedProduct.getId()).isEqualTo(1L);
	    
		verify(productService, times(1)).findProductById(1L);
		verify(productService, atLeastOnce()).findProductById(1L);
	}

	@Test
	void createProduct() {

		// given
		Product prod = new Product();
		given(productService.createProduct(prod)).willReturn(product);
		
		// when
		Product savedProduct = productService.createProduct(prod);

		// then
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isNotEqualTo(2L);
		assertThat(savedProduct.getId()).isEqualTo(1L);
			
		verify(productService, times(1)).createProduct(prod);
		verify(productService, atLeastOnce()).createProduct(prod);
		
	}

	@Test
	void sortProductsByName() {
		// given
		List<Product> prodList = new ArrayList<>();
		product2.setName("ABBS");
		product3.setName("MAAMS");
		product.setName("SAAS");
		
		prodList.add(product2);
		prodList.add(product3);
		prodList.add(product);
		
		List<Product> products = prodList
				.stream()
				.sorted()
				.collect(Collectors.toList());
		given(productService.getAllProducts()).willReturn(products);
		// when
		List<Product> returnedProductList = productService.getAllProducts();
		// then
		assertThat(returnedProductList).isNotNull();
		assertThat(returnedProductList.size()).isNotEqualTo(2);
		assertThat(returnedProductList.size()).isEqualTo(3);
		assertThat(returnedProductList).isSorted();
		assertThat(returnedProductList).isEqualTo(products);
		
		verify(productService, times(1)).getAllProducts();
		verify(productService, atLeastOnce()).getAllProducts();
	}

	@Test
	void deleteById() {

		doNothing().when(productService).deleteById(1L);
		productService.deleteById(1L);
		
		verify(productService, times(1)).deleteById(1L);
		verify(productService, atLeastOnce()).deleteById(1L);
	}

	@Test
	void updateProduct() {
		
	    ProductService productService = mock(ProductServiceImp.class);
	    
	    Product updatingProduct =  new Product();
		updatingProduct.setName("New Name");
		updatingProduct.setDescription(" New Description");
			
		Product savedProduct = new Product();
		savedProduct.setId(1L);
		savedProduct.setName(" MyName");
		savedProduct.setDescription(" Old Description");
		savedProduct.setCategoryId(111L);
		

		when(productService.updateProduct(updatingProduct, 1L))
		.thenReturn(savedProduct);

		if(updatingProduct.getName()!=null)
			savedProduct.setName(updatingProduct.getName());
		if(updatingProduct.getDescription()!=null)
			savedProduct.setDescription(updatingProduct.getDescription());
		if(updatingProduct.getCategoryId()!=null)
			savedProduct.setCategoryId(updatingProduct.getCategoryId());
		if(updatingProduct.getCreatedDate()!=null)
			savedProduct.setCreatedDate(updatingProduct.getCreatedDate());
		if(updatingProduct.getLastPurchaseDate()!=null)
			savedProduct.setLastPurchaseDate(updatingProduct.getLastPurchaseDate());
		Product newLyUpdatedProdUpdated = productService
				.updateProduct(updatingProduct, 1L);
		
		assertThat(newLyUpdatedProdUpdated.getName()).isEqualTo("New Name");
		assertThat(newLyUpdatedProdUpdated.getName()).isNotEqualTo("Old Name");

		
		verify(productService, times(1)).updateProduct(updatingProduct, 1L);
		verify(productService, atLeastOnce()).updateProduct(updatingProduct, 1L);

;
		
		
	}
}