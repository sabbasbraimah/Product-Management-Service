/**
 * 
 */
package com.seiduabbas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seiduabbas.domain.Product;

/**
 * @author Seidu Abbas
 *
 */
public interface ProductRepository extends JpaRepository<Product, Long>{

}
