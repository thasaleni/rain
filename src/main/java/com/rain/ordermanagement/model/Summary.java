package com.rain.ordermanagement.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Summary {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	@JoinColumn(name="order_id", nullable=false)
	private ShopOrder shopOrder;
    @OneToMany(cascade = CascadeType.ALL)
   	@JoinColumn(name = "order_id",referencedColumnName = "id",insertable = false,updatable = false)
	private Set<ItemSummary> itemSummary;
    public Summary(){}
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public ShopOrder getShopOrder() {
		return shopOrder;
	}
	public void setOrder(ShopOrder shopOrder) {
		this.shopOrder = shopOrder;
	}
	public Summary(Set<ItemSummary> itemSummary) {
    	this.itemSummary = itemSummary;
    }

	public Set<ItemSummary> getItemSummary() {
		return itemSummary;
	}

	public void setItemSummary(Set<ItemSummary> itemSummary) {
		this.itemSummary = itemSummary;
	}

}
