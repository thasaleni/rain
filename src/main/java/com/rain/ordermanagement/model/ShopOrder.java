package com.rain.ordermanagement.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;

@Entity
public class ShopOrder {
	@Id
	@GeneratedValue
	private Long id;
	@GeneratedValue
	private String reference;
	@CreatedDate
	private LocalDate date;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	private Double totalAmount;
	@OneToMany(mappedBy = "shopOrder")
	private Set<Summary> summaries = new HashSet<>();

	public ShopOrder() {
	}

	public ShopOrder(Account account, String reference, OrderStatus status) {
		this.account = account;
		this.reference = reference;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Account getAccount() {
		return account;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<Summary> getSummaries() {
		return summaries;
	}

	public void setSummaries(Set<Summary> summaries) {
		this.summaries = summaries;
	}

}
