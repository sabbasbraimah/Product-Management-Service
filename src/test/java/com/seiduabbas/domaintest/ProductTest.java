package com.seiduabbas.domaintest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.seiduabbas.domain.Product;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getUpdateDate() {
    }

    @Test
    void getId() {
    }

    @Test
    void getName() {
    }

    @Test
    void getDescription() {
    }

    @Test
    void getCategoryId() {
    }

    @Test
    void getCreatedDate() {
    }
    
    
    @Test
	public void compareTo() {
    	Product one = new Product();
    	one.setName("One");
    	Product two = new Product();
    	two.setName("Uwo");
    	
    	int ans = one.getName().compareTo(two.getName());
    	assertEquals(-6,ans);
    	
    	int ansTwo = two.getName().compareTo(one.getName());
    	assertEquals(6,ansTwo);

	}
}