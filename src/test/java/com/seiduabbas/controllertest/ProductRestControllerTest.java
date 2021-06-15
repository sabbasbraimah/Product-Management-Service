package com.seiduabbas.controllertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.seiduabbas.controller.ProductRestController;
import com.seiduabbas.domain.Product;
import com.seiduabbas.exception.ProductNotFoundException;
import com.seiduabbas.service.ProductServiceImp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private ProductServiceImp productServiceImp;
	
	@MockBean
	ProductRestController productController;
	
	private List<Product> productsList = new ArrayList<>();
	private Product product;

	@BeforeEach
	void setUp() {
		
		product = new Product();
		product.setId(1L); 		product.setName("One"); 

		
		productsList.add(new Product());
		productsList.add(new Product());
		productsList.add(new Product());
		//mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	
	@Test
	void restList() throws Exception {
		// given
		List<Product> productList = new ArrayList<>();

		Product p1 = new Product();
		p1.setId(1L); p1.setName("One");
		
		Product p2 = new Product();
		p2.setId(2L); 		p2.setName("Two");	
		
		Product p3 = new Product();
		p3.setId(3L); p3.setName("Three");

		productList.add(p1);
		productList.add(p2);
		productList.add(p3);

		when(productServiceImp.getAllProducts()).thenReturn(productList);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rest/restProducts")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = (MvcResult) mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andReturn();
		//JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		
	}

	

	@Test
	void restFindProduct() throws Exception {
		
		when(productServiceImp.findProductById(1L)).thenReturn(product);

		assertThat(product.getId()).isEqualTo(1L);
		assertThat(product.getId()).isNotEqualTo(2L);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rest/"+1)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = (MvcResult) mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().json("{id:1,name:One}" ))
				.andReturn();		
	}
	
	
	@Test
	void restApiEdit() throws Exception {
			
		when(productServiceImp.updateProduct(product,1L))
		.thenReturn(new Product(1L,"Namings", null));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/rest/"+1)
				.accept("application/json");

		MvcResult result =  mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().json("{id: 1,name:Ball}"))
				.andReturn();		
	}
	
	@Test
	void restApiSaveProduct() throws Exception {
		
		Product newproduct = new Product();
		newproduct.setName("New Product");
		
		Product createdNewProduct = new Product();
		createdNewProduct.setId(1L); createdNewProduct.setName("New Product");
		
		when(productServiceImp.createProduct(newproduct)).thenReturn(createdNewProduct);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rest")
				.accept(MediaType.APPLICATION_JSON)
				.param("id", "1")
				.param("name", "New Product");

		MvcResult result = (MvcResult) mockMvc.perform(requestBuilder)
				.andExpect(status().isCreated())
				.andExpect(content().json("{id: 1,name:New One}" ))
				.andReturn();
	}

	@Test
	void restApiDeleteProduct() throws Exception {
		
		doNothing().when(productServiceImp).deleteById( 1L);
		
		productServiceImp.deleteById(1L);
		
	    verify(productServiceImp, times(1)).deleteById(1L);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/rest/"+1)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = (MvcResult) mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().string(""))				
				.andReturn();
	}

	@Test
	void restApiSortProductsByName() throws Exception {
		
		
		// given
				List<Product> productList = new ArrayList<>();

				Product p1 = new Product();
				p1.setId(1L); p1.setName("One");
				
				Product p2 = new Product();
				p2.setId(2L); 		p2.setName("Two");	
				
				Product p3 = new Product();
				p3.setId(3L); p3.setName("Three");

				productList.add(p1);
				productList.add(p2);
				productList.add(p3);

				when(productServiceImp.sortProductsByName()).thenReturn(productList);
				
				RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rest/restSortByName")
						.accept(MediaType.APPLICATION_JSON);
				
				MvcResult result = (MvcResult) mockMvc.perform(requestBuilder)
						.andExpect(status().isOk())
						.andReturn();
				//JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
				

	}
}