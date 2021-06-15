/**
 * 
 */
package com.seiduabbas.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author Owner
 *
 */
@Entity
public class Category {

	@Id
	@GeneratedValue
	private Long id;
    private String name;

    
    public Category() {} 
    
    public Long getId() {
    	return id;
    }
    
	public String getCategoryName() {
		return name;
	}
	public void setCategoryName(String categoryName) {
		this.name = categoryName;
	}    
}
