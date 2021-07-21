package com.rain.ordermanagement.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account {
	@Id
	@GeneratedValue
	private Long id;
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JsonIgnore
	private User owner;
	@OneToMany(mappedBy = "account")
	private Set<ShopOrder> orders = new HashSet<>();
	public Account() {}
	public Account(User owner) {
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}


	public Set<ShopOrder> getOrders() {
		return orders;
	}

	public void setOrders(Set<ShopOrder> orders) {
		this.orders = orders;
	}

}
