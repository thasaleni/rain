package com.rain.ordermanagement.dto;

public class CodesDto {
	private String code;
	private Integer quantity;
	
	public CodesDto(String code, Integer quantity) {
		this.code = code;
		this.quantity = quantity;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	

}
