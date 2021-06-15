/**
 * 
 */
package com.seiduabbas.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.txw2.annotation.XmlElement;

/**
 * @author Seidu Abbas
 *
 */
@XmlRootElement
public class Products {
	
	private List<Product> products;

    @XmlElement
    public List<Product> getProductList() {
        if (products == null) {
        	products = new ArrayList<>();
        }
        return products;
    }
}
