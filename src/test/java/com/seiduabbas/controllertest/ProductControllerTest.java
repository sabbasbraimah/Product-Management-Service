package com.seiduabbas.controllertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seiduabbas.controller.ProductController;
import com.seiduabbas.domain.Product;
import com.seiduabbas.exception.ProductNotFoundException;
import com.seiduabbas.repository.ProductRepository;
import com.seiduabbas.service.ProductService;
import com.seiduabbas.service.ProductServiceImp;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

	@MockBean
	private ProductServiceImp productServiceImp ; 

	@MockBean
	private ProductController productController;

	@Mock
	private ProductRepository productRepo;

	@Autowired
	private MockMvc mockMvc;
	
	private List<Product> productsList = new ArrayList<>();

	@BeforeEach
	void setUp() {
		productsList.add(new Product());
		productsList.add(new Product());
		productsList.add(new Product());
		
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	void showProductListPage() throws Exception {
		// given
		mockMvc = MockMvcBuilders.standaloneSetup(productController)
				.setSingleView(new InternalResourceView("/products/prodictList.html")).build();

		mockMvc.perform(get("/products"))
		.andExpect(status().isOk())
		.andExpect(view().name("products"));
	}

	@Test
	void getNewProductForm() throws Exception {
		mockMvc.perform(get("/products/productForm"))
		.andExpect(status().isOk())
		.andExpect(view().name("products/productForm"));
	}

	@Test
	void findProduct() throws Exception {

		mockMvc.perform(get("/products/" + 1))
		.andExpect(status().isOk())
		.andExpect(view().name("products/" + 1));
	}

	@Test
	void testFindByIdNotFound() throws Exception {
		ProductNotFoundException pnfex = new ProductNotFoundException(200L);
		given(productServiceImp.findProductById(200L)).willThrow(pnfex);
		mockMvc.perform(get("/products/200")).andExpect(status().is(200));
	}

	@Test
	void initUpdateProduct() throws Exception {
		mockMvc.perform(get("/products/productForm"))
		.andExpect(status().isOk())
		.andExpect(view().name("products/productForm"));
	}

	@Test
	void processUpdateForm() throws Exception {
		//Product product = new Product();
		//product.setId(1L);
		//when(productRepo.findById(1L)).thenReturn(Optional.ofNullable(product));
		mockMvc.perform(post("/products/{productId}/update", 1))
		.andExpect(status().is(200));
	}

	@Test
	void testUpdateProductPostValid() throws Exception {
		
		mockMvc.perform(post("/products/{productId}/update", 9L)
				.param("name", "TV").param("description", "Something Good"))
		.andExpect(status().is(200));
	}

	@Test
	void saveProduct() throws Exception {
		// given
		Product newproduct = new Product();
		newproduct.setName("New Product");
		
		Product createdNewProduct = new Product();
		createdNewProduct.setId(1L); createdNewProduct.setName("New Product");
		
		when(productServiceImp.createProduct(newproduct)).thenReturn(createdNewProduct);
		
		
		
		MockHttpServletRequestBuilder createProduct = post("/products/new")
				.accept(MediaType.APPLICATION_JSON);			
		
		mockMvc.perform(createProduct)
        .andExpect(status().is(201))		
		.andExpect(view().name("redirect:/products/productShow"));
	}

	@Test
	void deleteProduct() {

	}

	@Test
	void productNotFound() {
	}

	@Test
	void sortProductsByName() {
	}

}