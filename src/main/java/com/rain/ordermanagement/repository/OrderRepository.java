package com.rain.ordermanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.rain.ordermanagement.model.ShopOrder;

public interface OrderRepository extends CrudRepository<ShopOrder, Long> {

	boolean existsByReference(String reference);

	ShopOrder findByReferenceAndAccountOwnerUsername(String reference, String ownerUsername);

	ShopOrder findByReference(String orderReference);

}
