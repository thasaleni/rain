package com.rain.ordermanagement.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.rain.ordermanagement.model.OrderStatus;

public class OrderDto {
    private String reference;
	private LocalDate date;
	private String ownerUsername;
	private OrderStatus status;
	private Long accountId;
	private CodesDto[]  itemCodes = {};

	public OrderDto(LocalDate date, String reference, String ownerUsername, OrderStatus status, Long accountId, Long userId) {
		this.date = date;
		this.reference = reference;
		this.ownerUsername = ownerUsername;
		this.status = status;
		this.accountId = accountId;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getOwnerUsername() {
		return ownerUsername;
	}

	public void setOwnerUsername(String ownerUsername) {
		this.ownerUsername = ownerUsername;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public CodesDto[]  getItemCodes() {
		return itemCodes;
	}

	public void setItemCodes(CodesDto[]  itemCodes) {
		this.itemCodes = itemCodes;
	}

}
