package com.rain.ordermanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ItemSummary {
	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	Item orderItem;
	Integer quantity;
	public ItemSummary() {}
	public ItemSummary(Item orderItem, Integer quantity) {
		this.orderItem = orderItem;
		this.quantity = quantity;
	}
	public Long getId() {
		return id;
	}
	public Item getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(Item orderItem) {
		this.orderItem = orderItem;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
