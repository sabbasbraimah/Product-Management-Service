/**
 * 
 */
package com.seiduabbas.domain;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author Owner
 *
 */
@Entity
public class Product implements Comparable<Product> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	private Long categoryId;

	@CreationTimestamp
	private LocalDateTime createdDate;

	@CreationTimestamp
	private LocalDateTime lastPurchaseDate;

	@UpdateTimestamp
	private LocalDateTime updateDate;

	public Product() {
		this.name = "";
	}

	/**
	 * @param id
	 * @param name
	 * @param createdDate
	 */
	public Product(Long id, String name, LocalDateTime createdDate) {
		super();
		this.id = id;
		this.name = name;
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime dateTime) {
		this.createdDate = dateTime;
	}

	public LocalDateTime getLastPurchaseDate() {
		return lastPurchaseDate;
	}

	public void setLastPurchaseDate(LocalDateTime lastPurchaseDate) {
		if (!lastPurchaseDate.isAfter(createdDate)) {
			throw new IllegalArgumentException("Last Purchase Date must be later than created date ");
		}
		this.lastPurchaseDate = lastPurchaseDate;
	}

	@Override
	public int compareTo(Product product) {
		return this.name.compareTo(product.name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(categoryId, other.categoryId) && Objects.equals(createdDate, other.createdDate)
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(lastPurchaseDate, other.lastPurchaseDate) && Objects.equals(name, other.name)
				&& Objects.equals(updateDate, other.updateDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId, createdDate, description, id, lastPurchaseDate, name, updateDate);
	}

}
