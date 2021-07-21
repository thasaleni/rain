package com.rain.ordermanagement.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.rain.ordermanagement.model.Item;

public interface OrderItemRepository extends CrudRepository<Item, Long> {

	Set<Item> findByCodeIn(Set<String> itemCodes);

}
