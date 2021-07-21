package com.rain.ordermanagement.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rain.ordermanagement.dto.OrderDto;
import com.rain.ordermanagement.service.OrderService;
@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value = "/createOrder")
	public ResponseEntity<? extends Object> createOrder(@RequestBody OrderDto orderDto) {
		OrderDto order = null;
		try {
			order = orderService.addOrder(orderDto);
		}catch(IllegalArgumentException e) {
			return  new ResponseEntity<String> (String.format("Error while creating order. %s", e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<OrderDto> (order, HttpStatus.CREATED);
	}
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value = "/updateOrderStatus")
	public ResponseEntity<? extends Object> updateOrder(@RequestBody OrderDto orderDto) {
		OrderDto order = null;
		try {
			order = orderService.updateOrder(orderDto);
		}catch(IllegalArgumentException e) {
			return  new ResponseEntity<String> (String.format("Error while updating order. %s", e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<OrderDto> (order, HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/order/{orderReference}")
	public ResponseEntity<? extends Object> getOrder(@PathVariable String orderReference) {
		OrderDto order = null;
		try {
			order = orderService.findByReference(orderReference);
			if(order == null) {
				return new ResponseEntity<String> (String.format("Order with ID: %s,  not found.", orderReference), HttpStatus.NOT_FOUND);
			}
		}catch(IllegalArgumentException e) {
			return  new ResponseEntity<String> (String.format("Error while looking for order. %s", e.getMessage()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<OrderDto> (order, HttpStatus.OK);
	}
}
